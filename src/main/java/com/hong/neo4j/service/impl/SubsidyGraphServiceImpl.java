package com.hong.neo4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.neo4j.converter.SubsidyGraphConverter;
import com.hong.neo4j.converter.SubsidyNodeConverter;
import com.hong.neo4j.converter.SubsidyRelationConverter;
import com.hong.neo4j.domain.SubsidyGraph;
import com.hong.neo4j.domain.SubsidyNode;
import com.hong.neo4j.domain.SubsidyRelation;
import com.hong.neo4j.dto.SubsidyDTO;
import com.hong.neo4j.dto.SubsidyGraphDTO;
import com.hong.neo4j.dto.SubsidyNodeDTO;
import com.hong.neo4j.dto.SubsidyRelationDTO;
import com.hong.neo4j.enums.SubsidyTypeEnum;
import com.hong.neo4j.mapper.SubsidyGraphMapper;
import com.hong.neo4j.repository.SubsidyRelationRepository;
import com.hong.neo4j.repository.SubsidyRepository;
import com.hong.neo4j.service.SubsidyGraphService;
import com.hong.neo4j.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 11:39
 * @Version : 1.0
 * Description     :
 */
@Service
public class SubsidyGraphServiceImpl extends ServiceImpl<SubsidyGraphMapper, SubsidyGraph> implements SubsidyGraphService {

	private final SubsidyRepository subsidyRepository;

	private final SubsidyGraphMapper subsidyGraphMapper;

	private final SubsidyGraphConverter subsidyGraphConverter;

	private final SubsidyNodeConverter subsidyNodeConverter;

	private final SubsidyRelationConverter subsidyRelationConverter;

	private final SubsidyRelationRepository subsidyRelationRepository;

	private static final Random random = new Random();

	public SubsidyGraphServiceImpl(SubsidyRepository subsidyRepository,
								   SubsidyGraphMapper subsidyGraphMapper,
								   SubsidyGraphConverter subsidyGraphConverter,
								   SubsidyNodeConverter subsidyNodeConverter,
								   SubsidyRelationConverter subsidyRelationConverter,
								   SubsidyRelationRepository subsidyRelationRepository) {
		this.subsidyGraphMapper = subsidyGraphMapper;
		this.subsidyRepository = subsidyRepository;
		this.subsidyGraphConverter = subsidyGraphConverter;
		this.subsidyNodeConverter = subsidyNodeConverter;
		this.subsidyRelationRepository = subsidyRelationRepository;
		this.subsidyRelationConverter = subsidyRelationConverter;
	}

	@Override
	@Transactional
	public void addGraphNode() {
		SubsidyGraph graph = addGraph();
		addNode(graph);
	}


	@Override
	public SubsidyDTO findById(String id) {
		SubsidyDTO dto = new SubsidyDTO();
		SubsidyGraph graph = subsidyGraphMapper.selectById(id);
		assert graph != null;
		dto.setGraphDTO(subsidyGraphConverter.toDTO(graph));

		List<SubsidyNode> nodes = subsidyRepository.findAllByGraphIdAndTitleAndNotEqualWithType(id, graph.getCoreNode(), SubsidyTypeEnum.NORMAL);
		assert nodes != null;
		dto.setSubsidyNodeDTOS(subsidyNodeConverter.toDTOList(nodes));

		List<SubsidyRelation> relations = subsidyRelationRepository.findAllByGraphId(id);
		assert relations != null;
		dto.setSubsidyRelationDTOS(subsidyRelationConverter.toDTOList(relations));

		List<SubsidyRelation> outerRelations = subsidyRelationRepository.findAllByStartNodeTitleAndEndNodeType(graph.getCoreNode(), SubsidyTypeEnum.NORMAL);
		if (outerRelations == null) outerRelations = new ArrayList<>();
		dto.setOuterRelationDTOS(subsidyRelationConverter.toDTOList(outerRelations));

		return dto;
	}

	@Override
	@Transactional
	public SubsidyDTO saveOrUpdate(SubsidyDTO subsidyDTO) {
		SubsidyGraphDTO graphDTO = subsidyDTO.getGraphDTO();
		List<SubsidyNodeDTO> nodeDTOS = subsidyDTO.getSubsidyNodeDTOS();
		List<SubsidyRelationDTO> relationDTOS = subsidyDTO.getSubsidyRelationDTOS();
		List<SubsidyRelationDTO> outerRelationDTOS = subsidyDTO.getOuterRelationDTOS();
		SubsidyGraph graph = subsidyGraphConverter.toEntity(graphDTO);
		if (StringUtils.isNotEmpty(graph.getId())) {
			subsidyGraphMapper.updateById(graph);
		} else {
			subsidyGraphMapper.insert(graph);
		}

		List<SubsidyNode> nodes = subsidyNodeConverter.toEntities(nodeDTOS);
		nodes.forEach(node -> node.setGraphId(graph.getId()));
		relationDTO2entities(relationDTOS, nodes);
		setupOuterRelationship(nodes, outerRelationDTOS, graph);
		subsidyRepository.saveAll(nodes);


		List<SubsidyRelation> relations = subsidyRelationRepository.findAllByGraphId(graph.getId());
		subsidyDTO.setGraphDTO(subsidyGraphConverter.toDTO(graph));
		subsidyDTO.setSubsidyNodeDTOS(subsidyNodeConverter.toDTOList(nodes));
		subsidyDTO.setSubsidyRelationDTOS(subsidyRelationConverter.toDTOList(relations));


		return subsidyDTO;
	}

	@Override
	public List<SubsidyNodeDTO> findAllPolicyNodeByTitle(String coreNode) {
		List<SubsidyNode> nodes = subsidyRepository.findAllByTypeAndTitle(SubsidyTypeEnum.NORMAL, coreNode);
		return subsidyNodeConverter.toDTOList(nodes);
	}

	@Override
	public List<SubsidyNodeDTO> findAllPolicyNode() {
		List<SubsidyNode> nodes = subsidyRepository.findAllByType(SubsidyTypeEnum.NORMAL);
		return subsidyNodeConverter.toDTOList(nodes);
	}

	@Override
	public List<SubsidyGraphDTO> findAllGraph() {
		List<SubsidyGraph> graphs = subsidyGraphMapper.selectList(null);
		return subsidyGraphConverter.toDTOList(graphs);
	}

	@Override
	public List<SubsidyGraphDTO> findAllGraphByTitleOrContent(String keyword) {
		QueryWrapper<SubsidyGraph> wrapper = new QueryWrapper<>();
		wrapper.like("title", "%" + keyword + "%").or().like("description", "%" + keyword + "%");
		List<SubsidyGraph> graphs = subsidyGraphMapper.selectList(wrapper);
		return subsidyGraphConverter.toDTOList(graphs);
	}

	@Override
	@Transactional
	public boolean deleteById(String id) {
		SubsidyGraph graph = subsidyGraphMapper.selectById(id);
		if (graph == null) return false;
		subsidyGraphMapper.deleteById(id);
		subsidyRepository.deleteAllByGraphId(id);
		return true;
	}

	@Override
	public Integer insertBatchGraph(List<SubsidyGraph> graphs) {
		if (graphs == null || graphs.size() == 0) return 0;
		return subsidyGraphMapper.insertBatchSomeColumn(graphs);
	}

	@Override
	public Integer insertBatchNode(List<SubsidyNode> nodes) {
		if (nodes == null || nodes.size() == 0) return 0;
		subsidyRepository.saveAll(nodes);
		return nodes.size();
	}

	@Override
	public Map<String, String> findAllGraphsIdAndName() {
		List<SubsidyGraph> graphs = subsidyGraphMapper.findAllGraphsIdAndName();
		Map<String, String> map = new HashMap<>();
		for (SubsidyGraph graph : graphs) {
			map.put(graph.getName(), graph.getId());
		}
		return map;
	}

	@Override
	public boolean deleteLinkById(Long id) {
		try {
			subsidyRelationRepository.deleteById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteNodeById(Long id) {
		try {
			subsidyRepository.deleteById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<SubsidyRelationDTO> findAllRelationByStartCoreNodeAndEndType(String coreNode, SubsidyTypeEnum type) {
		List<SubsidyRelation> relations = subsidyRelationRepository.findAllByStartNodeTitleAndEndNodeType(coreNode, type);
		return subsidyRelationConverter.toDTOList(relations);
	}

	@Override
	public boolean deleteRelationById(Long id) {
		try {
			subsidyRelationRepository.deleteById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}


	private void setupOuterRelationship(List<SubsidyNode> nodes, List<SubsidyRelationDTO> relationDTOS, SubsidyGraph graph) {
		// 设置外部连接关系
		List<SubsidyNode> policyNodes = subsidyRepository.findAllByType(SubsidyTypeEnum.NORMAL);
		Map<String, SubsidyNode> nodeMap = new HashMap<>();
		for (SubsidyNode node : policyNodes) {
			nodeMap.put(node.getTitle(), node);
		}
		SubsidyNode coreNode = null;
		for (SubsidyNode node : nodes) {
			if (node.getTitle().equals(graph.getCoreNode())) {
				coreNode = node;
				break;
			}
		}

		if (coreNode != null) {
			for (SubsidyRelationDTO relation : relationDTOS) {
				if (nodeMap.containsKey(relation.getEndNodeName())) {
					SubsidyNode node = nodeMap.get(relation.getEndNodeName());
					node.setLineContent(relation.getComment());
					coreNode.addOnePointing(node, relation.getId());
				}
			}
		}
	}

	private void relationDTO2entities(List<SubsidyRelationDTO> dtos, List<SubsidyNode> nodes) {
		Map<String, SubsidyNode> nodeMap = new HashMap<>();
		for (SubsidyNode node : nodes) {
			nodeMap.put(node.getTitle(), node);
		}

		for (SubsidyRelationDTO dto : dtos) {
			String startNodeName = dto.getStartNodeName();
			String endNodeName = dto.getEndNodeName();
			if (nodeMap.containsKey(startNodeName) && nodeMap.containsKey(endNodeName)) {
				SubsidyNode startNode = nodeMap.get(startNodeName);
				SubsidyNode endNode = nodeMap.get(endNodeName);
				endNode.setLineContent(dto.getComment());
				startNode.addOnePointing(endNode, dto.getId());
			}
		}

	}


	private SubsidyGraph addGraph() {
		SubsidyGraph graph = new SubsidyGraph();
		graph.setTitle("关于全面推开农业\"三项补贴\"改革工作的通知");
		graph.setName("关于全面推开农业\"三项补贴\"改革工作的通知");
		graph.setType("政策");
		graph.setProof("财农 (2016) 26号");
		graph.setMeans("财政补贴");
		graph.setLocation("中央");
		graph.setContentType("农业农资");
		graph.setOrganization("财政部农业司");
		graph.setIndustry("农林渔牧");
		graph.setKeywords("农业,农贸综合补贴,生产建设兵团,补贴,农业信贷担保");
		graph.setContent("<h2 style=\"text-align: center;\"><img class=\"anchorclass\"/>财农〔2016〕26号</h2><p>各省、自治区、直辖市、计划单列市财政厅（局）、农业（农牧、农村经济）厅（局、委员会）：</p><p>　　2015年，经国务院同意，财政部、农业部印发了《关于调整完善农业三项补贴政策的指导意见》（财农〔2015〕31号），在全国范围内从农资综合补贴中调整20%的资金，加上种粮大户补贴试点资金和农业“三项补贴”增量资金，统筹用于支持粮食适度规模经营，重点用于支持建立完善农业信贷担保体系，同时选择部分省开展试点，将农作物良种补贴、种粮农民直接补贴和农资综合补贴合并为农业支持保护补贴，政策目标调整为支持耕地地力保护和粮食适度规模经营。从试点情况看，调整完善农业“三项补贴”政策方向正确，目标明确，操作简便，取得了预期效果。在总结试点经验的基础上，2016年在全国全面推开农业“三项补贴”改革，现将有关事项通知如下：" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　一、重要意义</p><p>　　近年来，党中央、国务院高度重视农业补贴政策的有效实施，明确要求在稳定加大补贴力度的同时，逐步完善补贴政策，改进补贴办法，提高补贴效能。推进农业“三项补贴”改革，是按照中央“稳增长、促改革、调结构、惠民生”总体部署做出的重大政策调整，是主动适应经济发展新常态、顺应农业发展新形势的重要举措，是供给侧结构性改革在农业生产领域的具体体现。全面推开农业“三项补贴”改革以绿色生态为导向，推进农业“三项补贴”由激励性补贴向功能性补贴转变、由覆盖性补贴向环节性补贴转变，提高补贴政策的指向性、精准性和实效性。各地要充分认识全面推开农业“三项补贴”改革的重要意义，把思想和行动统一到中央的决策部署上来，精心组织，周密部署，确保改革工作平稳顺利推进。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（一）有利于提高政策的指向性、精准性和实效性。将农业“三项补贴”中直接发放给农民的补贴与耕地地力保护挂钩，明确撂荒地、改变用途等耕地不纳入补贴范围，鼓励农民秸秆还田，不露天焚烧，主动保护耕地地力，加强农业生态资源保护意识，实现“藏粮于地”，使政策目标指向更加精准，政策效果与政策目标更加一致，促进了支农政策“黄箱”改“绿箱”，进一步拓展了支持农业发展和农民增收的政策空间。同时，统一资金审核和发放程序，减少了工作环节，减轻了基层负担，节约了时间和成本，提高了工作效率。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（二）有利于促进粮食适度规模经营。当前农业虽然保持增量增收的好势头，但数量与质量、总量与结构、投入与产出、成本与效益、生产与环境等方面矛盾日益上升，特别是家庭小规模经营仍占大多数，一定程度上限制了农业劳动生产率的提高，影响了农业现代化进程。通过政策引导，加快培育新型经营主体、培养新型职业农民，鼓励多种形式的粮食适度规模经营，有利于推动农业生产加快进入规模化、产业化、社会化发展新阶段，符合现代农业发展方向。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（三）有利于推动农村金融加快发展。长期以来，农民“融资难、融资贵”问题始终得不到很好解决，一定程度上影响了农业农村发展和农民增收致富。通过调整部分资金支持建立健全农业信贷担保体系，并强调其政策性、独立性和专注性，既是撬动金融和社会资本支持现代农业建设，有效缓解农业农村发展资金不足问题的重要手段，也是新常态下创新财政支农机制，放大财政支农政策效应的重要举措，同时兼顾了效率与公平，适应农业产业升级对金融支持的需要，也有利于推动农村金融发展。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　二、主要内容</p><p>　　2016年起，在全国全面推开农业“三项补贴”改革，即将农业“三项补贴”合并为农业支持保护补贴，政策目标调整为支持耕地地力保护和粮食适度规模经营。中央财政已将2016年用于耕地地力保护的农业支持保护补贴资金全部提前下达，其中下达黑龙江省、广东省和新疆维吾尔自治区的资金，包含了需兑付给直属垦区农场和兵团团场职工的用于耕地地力保护的资金，由农业部直属垦区、新疆生产建设兵团商当地省级财政、农业部门研究落实；年度执行中下达的农业支持保护补贴资金全部用于支持粮食适度规模经营；中央财政通过上划部门预算下达农业部直属垦区、新疆生产建设兵团、中储粮总公司的农业支持保护补贴资金，全部用于支持粮食适度规模经营。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（一）加强耕地地力保护。用于耕地地力保护的补贴资金，其补贴对象原则上为拥有耕地承包权的种地农民；补贴依据可以是二轮承包耕地面积、计税耕地面积、确权耕地面积或粮食种植面积等，具体以哪一种类型面积或哪几种类型面积，由省级人民政府结合本地实际自定；补贴标准由地方根据补贴资金总量和确定的补贴依据综合测算确定。对已作为畜牧养殖场使用的耕地、林地、成片粮田转为设施农业用地、非农业征（占）用耕地等已改变用途的耕地，以及长年抛荒地、占补平衡中“补”的面积和质量达不到耕种条件的耕地等不再给予补贴。鼓励各地创新方式方法，以绿色生态为导向，提高农作物秸秆综合利用水平，引导农民综合采取秸秆还田、深松整地、减少化肥农药用量、施用有机肥等措施，切实加强农业生态资源保护，自觉提升耕地地力。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（二）促进粮食适度规模经营。用于粮食适度规模经营的补贴资金，原则上以2016年的规模为基数，每年从农业支持保护补贴资金中予以安排，以后年度根据农业支持保护补贴的预算安排情况同比例调整，支持对象重点向种粮大户、家庭农场、农民合作社和农业社会化服务组织等新型经营主体倾斜，体现“谁多种粮食，就优先支持谁”。各地要坚持因地制宜、简便易行、效率与公平兼顾的原则，进一步优化资源配置，提高农业生产率、土地产出率和资源利用率。鼓励各地创新新型经营主体支持方式，采取贷款贴息、重大技术推广与服务补助等方式支持新型经营主体发展多种形式的粮食适度规模经营，不鼓励对新型经营主体采取现金直补。对新型经营主体贷款贴息可按照不超过贷款利息的50%给予补助。对重大技术推广与服务补助，可以采取“先服务后补助”、提供物化补助等方式。要加快推进农业社会化服务体系建设，在粮食生产托管服务、病虫害统防统治、农业废弃物资源化利用、农业面源污染防治等方面，积极采取政府购买服务等方式支持符合条件的经营性服务组织开展公益性服务，积极探索将财政资金形成的资产折股量化到组织成员。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　近几年，用于粮食适度规模经营的补贴资金，要按照财政部、农业部、银监会印发的《关于财政支持建立农业信贷担保体系的指导意见》（财农〔2015〕121号）要求，重点支持建立健全农业信贷担保体系，中央财政下达地方用于支持粮食适度规模经营的农业支持保护补贴资金统筹用于资本金注入、担保费用补助、风险补偿等方面，通过强化银担合作机制，着力解决新型经营主体在粮食适度规模经营中的“融资难、融资贵”问题，力争用3年时间建成政策性、独立性、专注于农业、覆盖全国的农业信贷担保体系。各地要充分发挥财政注入资本金的作用，尽快启动农业信贷担保业务运营，并根据业务开展情况，合理确定财政注入资本金的规模和节奏。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　三、保障措施</p><p>　　农业“三项补贴”改革事关广大农民群众切身利益和农业农村发展大局，事关国家粮食安全和农业可持续发展，地方各级人民政府及财政、农业部门要切实加强组织领导，细化政策措施，注重宣传引导，加大工作力度，确保完成各项改革任务。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（一）加强组织领导。农业“三项补贴”改革工作由省级人民政府负总责，地方各级财政部门、农业部门具体组织实施。要建立健全工作机制，明确责任分工，密切部门合作，抓好工作落实。要结合本地区实际，抓紧制定实施方案，务必于6月30日前将需兑现到农民手中的补贴资金发放到位，让农民群众吃上“定心丸”。要做好政策宣传和舆论引导工作，主动与社会各方面特别是基层干部和农民群众进行沟通交流，赢得理解和支持。地方各级财政部门要安排相应工作经费，保障各项工作有序推进。各省份实施方案在报送省级人民政府审定前要与财政部、农业部充分沟通，正式印发后要及时报送财政部、农业部备案。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（二）加强资金管理。中央财政农业支持保护补贴资金按照耕地面积、粮食产量、适度规模经营发展等因素测算切块到省级财政，由各省份结合本地实际确定补贴对象、补贴方式和补贴标准。省级财政、农业部门要切实做好资金拨付和监管工作，督促县级财政、农业部门做好基础数据采集审核、补贴资金发放等工作。农业“三项补贴”改革后，中央财政不再安排农作物良种补贴资金，各地农作物良种推广工作可以根据需要从上级和本级财政安排的农业技术推广与服务补助资金中统筹解决。对于骗取、套取、贪污、挤占、挪用农业支持保护补贴资金的，或违规发放补贴资金的行为，要依法依规严肃处理。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p>　　（三）加强督导考核。各省级财政、农业部门要密切跟踪农业“三项补贴”改革工作情况，加强信息沟通，重大问题及时报告财政部、农业部。财政部、农业部将强化监管督导检查，研究制定资金管理办法和绩效管理制度，适时对各地农业支持保护补贴政策落实情况开展绩效考核，考核结果将作为以后年度农业支持保护补贴资金分配的重要因素。" +
				"&nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"text-align: right;\">　　财政部 农业部</p><p style=\"text-align: right;\">　　2016年4月18日</p>");
		subsidyGraphMapper.insert(graph);
		return graph;
	}

	private void addNode(SubsidyGraph graph) {
		SubsidyNode node = new SubsidyNode("关于全面推开农业\"三项补贴\"改革工作的通知", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), "引用", new String[]{"标签1"});
		SubsidyNode node1 = new SubsidyNode("事件1", graph.getId(), 0, new Date(), SubsidyTypeEnum.EVENT, buildContent(), "属性", new String[]{"标签2"});
		SubsidyNode node2 = new SubsidyNode("事件2", graph.getId(), 0, new Date(), SubsidyTypeEnum.EVENT, buildContent(), "属性", new String[]{"标签3"});
		node.addPointing(node1, node2);
		SubsidyNode node3 = new SubsidyNode("补贴对象A", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), "属性", new String[]{"标签4"});
		SubsidyNode node4 = new SubsidyNode("补贴对象B", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), "属性", new String[]{"标签5"});
		SubsidyNode node5 = new SubsidyNode("补贴类型1", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TYPE, buildContent(), "属性", new String[]{"标签6"});
		node1.addPointing(node3, node4, node5);
		SubsidyNode node6 = new SubsidyNode("补贴对象C", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), "属性", new String[]{"标签7"});
		SubsidyNode node7 = new SubsidyNode("补贴对象D", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TARGET, buildContent(), "属性", new String[]{"标签8"});
		SubsidyNode node8 = new SubsidyNode("补贴类型2", graph.getId(), 0, new Date(), SubsidyTypeEnum.SUBSIDY_TYPE, buildContent(), "属性", new String[]{"标签9"});
		node2.addPointing(node6, node7, node8);

		SubsidyNode node9 = new SubsidyNode("某指导意见(政策)", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), "引用", new String[]{"标签10"});
		SubsidyNode node10 = new SubsidyNode("政策A", graph.getId(), 0, new Date(), SubsidyTypeEnum.NORMAL, buildContent(), "", new String[]{"标签10"});
		node.addPointing(node9);
		node10.addPointing(node);

		graph.setCoreNode(node.getTitle());
		subsidyGraphMapper.updateById(graph);
		subsidyRepository.saveAll(Stream.of(node, node1, node2, node3, node4, node5, node6, node7, node8, node9, node10).collect(Collectors.toList()));
	}

	private String buildContent() {
		String[] contents = {
				"根据国家下达我省市县农业\"三项补贴\"资金规模和市县应补贴面积，2016年全省市耕地地力保护补贴标准为每亩71.46元，其中，秸秆还田，施用农家肥等直接用于耕地力提升的资金要达到每亩10元以上"
		};

		return contents[random.nextInt(contents.length)];

	}


}
