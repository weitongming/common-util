//package com.weitongming.util;
//
//
//import backtype.storm.task.OutputCollector;
//import backtype.storm.task.TopologyContext;
//import backtype.storm.topology.IRichBolt;
//import backtype.storm.topology.OutputFieldsDeclarer;
//import backtype.storm.tuple.Fields;
//import backtype.storm.tuple.Tuple;
//import backtype.storm.tuple.Values;
//import com.sgmw.core.SpringContext;
//import com.sgmw.core.utils.HexUtils;
//import com.sgmw.node.BoTaiStormApplication;
//import com.sgmw.node.dao.impl.TerminalDataDao;
//import com.sgmw.node.utils.ConfigUtil;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import shade.storm.org.json.simple.JSONObject;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Bolt,转换
// */
//public class ParseBolt implements IRichBolt {
//    private final static Logger logger = LoggerFactory.getLogger(ParseBolt.class);
//    private OutputCollector collector;
//    private Map<String, List<PartInfo>> markToPartInfos;
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
////    public void initMarkToPartInfos() {
////        int count = 0;
////        int max = 3;
////        while (count < max) {
////            count++;
////            try {
////                TerminalDataDao partInfoDao = (TerminalDataDao) SpringContext.getInstance().getBean(TerminalDataDao.class);
////
////                List<PartInfo> partInfos = partInfoDao.queryAll(PartInfo.class, "select * from botai_partinfo");
////                markToPartInfos = new LinkedHashMap<>();
////                for (PartInfo partInfo : partInfos) {
////                    String mark = partInfo.getMark();
////                    List<PartInfo> subList = null;
////                    if (markToPartInfos.containsKey(mark) == false) {
////                        subList = new ArrayList<>();
////                        markToPartInfos.put(mark, subList);
////                    } else {
////                        subList = markToPartInfos.get(mark);
////                    }
////                    subList.add(partInfo);
////                }
////                return;
////         /*   String message = "01107e014c5a574144414741344b473130353034367e02503836363739333033303534323639337e03007e04006c7e05097e062a7e074ef727a47e08207e090001ce7e107d014ef727a47d027c0506857fff460173291f7c08000009607c0900cf7c0b677c0e2d7c107c11027c1202007c1301af7c1b000009647c1d067c240000007c2700005e7c29004ef6a7a57c3a03287c40000195467c57017c7000007c710000000000000000000000007c7242414e445f455f555452415f4f5045524154494e475f42414e445f337c737b013436307b0230337b0300007b0400001ec17c74017c75000000017c76000000000000007c77000271000000000000007c7800ff001e0000000000007c79028a017c80010001012c02012c0200007c8100017c820000010100000003007c83007c840000090e000008bb000008bb000008687c85000000007c86007c87007c88007c8913120e0e7c8a007c8b007c8c007c8d0000000000000000000000000000007c8e0001010000007c8f0000007c90007c91007c92007c93010000007c9400007c9500007c960000007c97007c9800007c990000000000007c9a00000000000000000000007c9b0000007c9c0000007c9d00058b7c9e00377c9f017ca1000000000000010101017ca27ffd7ffd7ffd7ffd007ca3000000007ca400007ca5000000000000000000000000007ca6007ca70000000000000000000000000000000000";
////            message = "01107e014c5a574145414741394b423939373534197e02503836363739333033303831313738227e03007e04001b7e05097e062a7e074ef508aa7e08207e090001d17e107d014ef5089b7d027c050714004d01e91bed7c08000001cc7c09013d7c0b767c0e347c100a0d14177c11027c1202007c1301bf7c1b000000047c1d077c240000007c2700fffd7c29004ef4889c7c3a06327c40000173187c57007c7000007c710000000000000000000000007c7242414e445f455f555452415f4f5045524154494e475f42414e445f337c737b013436307b0230337b0300007b04000081247c74017c75000900017c76000000000000007c7700488bc70000010000007c7800ff071e00000e0007007c79028a017c8000000200aa0100aa0100007c8100017c820000000000000000007c83027c8400000dbf00000693000007390000071d7c85070707077c86017c87007c88007c897ffd7ffe7ffe7ffe7c8a007c8b007c8c007c8d0400000202020100000400000000007c8e0001010000007c8f0000007c90007c91007c92007c93010000017c9401017c9502007c960000007c97f77c9802007c990200000000007c9a00000000000000000000007c9b0000007c9c0000007c9d0005897c9e00347c9f017ca1000000000000000000007ca200010000647ca3000000007ca400007ca5000000000000000000000000007ca6007ca70000000000000000000000000000000000";
////            Map dataMap = ProcessorManager.receive(message);*/
////            } catch (Exception e) {
////                logger.error("query botai_partinfo error", e);
////            }
////        }
////    }
//
//    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
//        this.collector = collector;
//       /* Resource fileRource = new ClassPathResource("convert.properties");
//        convertMap = new Properties();
//        try {
//            convertMap.load(fileRource.getInputStream());
//        } catch (IOException e) {
//            logger.error("ParseBolt prepare error", e);
//        }*/
//        //logger.info("ParseBolt prepare"+Tad.currentThread().getName());
//        BoTaiStormApplication.run(new String[0]);
//        markToPartInfos = ConfigUtil.getInstance().getMarkToPartInfos();
//    }
//
//    public void execute(Tuple t) {
//        try {
//            String message = t.getString(0);
//            if (message.length() < 4) {
//                return;
//            }
//            //logger.info("parseBolt-receive");
//            //Map dataMap = ProcessorManager.receive(message);
//            Map dataMap = receive(message, markToPartInfos);
//            //logger.info(terminalId+"-----12345"+Thread.currentThread().getName());
//            //dataMap.put("terminalId",terminalId);
//            //  logger.info("parseBolt-emit:"+parsedMessage);
//            if (dataMap != null) {
//                String vin = dataMap.get("vin").toString();
//                if (StringUtils.isNotBlank(vin)) {
//                    String busiTime = dataMap.get("ngtpCreateTime").toString();
////                    Date date = sdf.parse(busiTime);
////                    if (new Date().getTime() - date.getTime() > 1000 * 60 * 60) {
////                        return;
////                    }
//                    String parsedMessage = ConvertUtils.toJson(dataMap);
//                    collector.emit(t, new Values(vin, busiTime, parsedMessage));
//                }
//            }
//        } catch (Exception e) {
//            logger.error("parse terminaldata error:" + t.getString(0), e);
////            collector.fail(t);
//        }
//    }
//
//    Map receive(String mesg, Map<String, List<PartInfo>> markToPartInfos) throws Exception {
//       /* String gps = mesg.substring(mesg.indexOf("7c29")+6,mesg.indexOf("7c3a"));
//        String gpstime = ConvertUtils.convertToTime(HexUtils.hex2Bytes(gps));*/
//        Map<String, Object> resultMap = new LinkedHashMap<>();
//        List<String> marks = new ArrayList<>(markToPartInfos.keySet());
//        if (mesg == null || mesg.trim().equals("")) {
//            return null;
//        }
//        //协议版本号
//        resultMap.put("protocolVersion", ConvertUtils.readUnsignedByte(HexUtils.hex2Bytes(mesg.substring(2, 4))[0]));
//        int index = 4;
//        //有符号数列
//        List<String> signeds = Arrays.asList("rssi", "engineWaterTemperature", "spdLimWarmOfseSetSt", "trgDecelValForAebEba", "lfTyreTemperature", "rfTyreTemperature", "laTyreTemperature", "RaTyreTemperature");
//        //TODO:假定分隔符不重复
//        for (int i = 0; i < marks.size(); i++) {
//           /* String part = parts[i];
//            String separator = part.substring(0, 2);
//            String content = part.substring(2);
//            byte[] contentBytes = HexUtils.hex2Bytes(content);*/
//            String mark = marks.get(i);
////            if (mark.equals("7c78")) {
////                System.out.println();
////            }
//            //String mark = "7e" + separator;
//            int start = mesg.indexOf(mark, index);
//            if (start == -1) {
//                continue;
//            }
//            //寻找每一个字段的结束位置
//            int end = -1;
//            List<PartInfo> partInfos = markToPartInfos.get(mark);
//            //先长度累加
//            int len = 0;
////            if (mark.indexOf("7e") == 0) {
////                for (PartInfo partInfo : partInfos) {
////                    if (partInfo.getLength() != null) {
////                        len += partInfo.getLength();
//////                        end = start + 4 + len;
//////                        index = end;
////                    }
////                }
////            } else {
////                for (int j = i + 1; j < marks.size(); j++) {
////                    int indx = mesg.indexOf(marks.get(j), start);
////                    if (indx != -1) {
////                        end = indx;
////                        break;
////                    }
////                }
////                if (end == -1) {//结尾
////                    end = mesg.length();
////                }
////            }
//            //只有头部不进行转义
//            if (mark.indexOf("7e") == 0) {
//                for (PartInfo partInfo : partInfos) {
//                    if (partInfo.getLength() != null) {
//                        len += partInfo.getLength();
//                    }
//                }
//            }
//            //没有长度就找下一个标识符，这样要求找到下一个mark前,mark一定不能在内容中
//            if (len == 0) {
//                for (int j = i + 1; j < marks.size(); j++) {
//                    int indx = mesg.indexOf(marks.get(j), start);
//                    if (indx != -1) {
//                        end = indx;
//                        break;
//                    }
//                }
//                if (end == -1) {//结尾
//                    end = mesg.length();
//                }
//            } else {
//                end = start + 4 + len * 2;
//            }
//
//            index = end;
//            String content = mesg.substring(start + 4, end);
//            content = content.replaceAll("7ffe", "7e").replaceAll("7ffc", "7c").replaceAll("7ffd", "7d").
//                    replaceAll("7ffa", "7a").replaceAll("7ffb", "7b").replaceAll("7fff", "7f");
//            byte[] contentBytes = HexUtils.hex2Bytes(content);
//            if (mark.equals("7c73")) {//蜂窝网络及相关代码
//                String s1 = content.substring(content.indexOf("7b01") + 4, content.indexOf("7b02"));
//                if (StringUtils.isBlank(s1)) {
//                    resultMap.put("cellularNetworkCountryCode", "");
//                } else {
//                    byte[] b1 = HexUtils.hex2Bytes(s1);
//                    String r1 = ConvertUtils.readString(b1, 0, b1.length);
//                    resultMap.put("cellularNetworkCountryCode", r1);
//                }
//                String s2 = content.substring(content.indexOf("7b02") + 4, content.indexOf("7b03"));
//                if (StringUtils.isBlank(s2)) {
//                    resultMap.put("cellularNetworkCode", "");
//                } else {
//                    byte[] b2 = HexUtils.hex2Bytes(s2);
//                    String r2 = ConvertUtils.readString(b2, 0, b2.length);
//                    resultMap.put("cellularNetworkCode", r2);
//                }
//                String s3 = content.substring(content.indexOf("7b03") + 4, content.indexOf("7b04"));
//                byte[] b3 = HexUtils.hex2Bytes(s3);
//                Integer r3 = ConvertUtils.readUnsignedInteger(b3, 0, b3.length);
//                resultMap.put("regionalAreaCode", r3);
//                String s4 = content.substring(content.indexOf("7b04") + 4);
//                byte[] b4 = HexUtils.hex2Bytes(s4);
//                Long r4 = ConvertUtils.readUnsignedLong(b4, 0);
//                resultMap.put("baseStationCode", r4);
//                continue;
//            }
//            int offset = 0;
//            String tyreAirPressure = "";
//            for (int j = 0; j < partInfos.size(); j++) {
//                PartInfo partInfo = partInfos.get(j);
//                Object b = null;
//                //VIN,TBOX PDSN号
//                if (partInfo.getMark().equals("7e01") || partInfo.getMark().equals("7e02")) {
//                    b = ConvertUtils.convertHexToAscii(content);
//                }
//                //timeStamp,采集时间
//                else if (partInfo.getMark().equals("7e07") || partInfo.getMark().equals("7d01")) {
//                    b = ConvertUtils.convertToTime(contentBytes);
//                    //TODO
////                    Date date = sdf.parse(b.toString());
////                    Date now = new Date();
////                    if (now.getTime() - date.getTime() > 1000 * 60 * 60) {
////                        if (now.getTime() % 100 == 0) {
////                            logger.info("currentDateTime:" + b);
////                        }
////                        return null;
////                    }
//                } else if (mark.equals("7c96")) {//有三种可能长度
//                    if (content.length() == 2) {
//                        b = ConvertUtils.readUnsignedByte(contentBytes[0]);
//                    } else {
//                        b = ConvertUtils.readUnsignedInteger(contentBytes, 0, content.length() / 2);
//                    }
//                } else if (mark.equals("7c10")) {//告警信息
//                    if(ArrayUtils.isNotEmpty(contentBytes)){
//                        List<String> strings = ConvertUtils.byteToList(contentBytes);
//                        if(CollectionUtils.isNotEmpty(strings)){
//                            b = String.join(",", strings);
//                        }
//                    }
//                } else if (mark.equals("7c72")) {//网络频段
//                    if (contentBytes == null || contentBytes.length == 0) {
//                        b = "";
//                    } else {
//                        b = ConvertUtils.readString(contentBytes, 0, contentBytes.length);
//                    }
//                } else if (partInfo.getLength() != null) {
//                    if (offset >= contentBytes.length) {
//                        break;
//                    }
//                    if (partInfo.getLength() == 1) {
//                        //TODO:byte有可能第一位为1,这时候要看是有符号数还是无符号数，如果是无符号数这里要改
//                        b = contentBytes[offset];
//                        //  if0(!mark.equals("rssi")){//有符号
//                        if (!signeds.contains(partInfo.getName())) {
//                            b = ConvertUtils.readUnsignedByte(contentBytes[offset]);
//                        }
//                        offset++;
//                    } else if (partInfo.getLength() == 2 || partInfo.getLength() == 3) {
//                        b = ConvertUtils.readUnsignedInteger(contentBytes, offset, partInfo.getLength());
//                        offset = offset + partInfo.getLength();
//                    } else if (partInfo.getLength() == 4) {
//                        if (partInfo.getName().equals("gpsTime")) {
//                            b = ConvertUtils.convertToTime(ArrayUtils.subarray(contentBytes, offset, contentBytes.length));
//                            resultMap.put("gpsTime", b);
//                            continue;
//                        } else {
//                            b = ConvertUtils.readUnsignedLong(contentBytes, offset);
//                            offset = offset + 4;
//                        }
//                    }
//                    //}
//                    BigDecimal a = new BigDecimal(b.toString());
//                    if (partInfo.getFactor() != null) {
//                        BigDecimal factor = new BigDecimal(partInfo.getFactor().toString());
//                        int l = ConvertUtils.cacluateDecimal(factor);
//                        //electricValue是1位小数
//                        if (partInfo.getName().equals("electricValue") || mark.equals("7c84")) {
//                            l = 1;
//                        }
//                        BigDecimal result = a.multiply(factor);
//                        if (partInfo.getOffset() != null) {
//                            //TODO:暂时不考虑offset有小数时
//                            result = result.add(new BigDecimal(partInfo.getOffset()));
//                        }
//                        if (l > 0) {
//                            b = result.setScale(l, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        } else {
//                            if (b instanceof Long) {
//                                b = result.longValue();
//                            } else if (b instanceof Integer) {
//                                b = result.intValue();
//                            } else if (b instanceof Short) {
//                                b = result.shortValue();
//                            }
//                        }
//                    } else if (partInfo.getOffset() != null) {
//                        //TODO:暂时不考虑offset有小数时
//                        BigDecimal result = a.add(new BigDecimal(partInfo.getOffset()));
//                        b = b instanceof Integer ? result.intValue() : result.longValue();
//                    }
//                } else {
//
//                }
//
//                if (mark.equals("7c24")) {
//                    //tbox取不到有效值，不发kafka。
//                } else if (mark.equals("7c84") && i == 0) {
//                    resultMap.put("lfPressure", b);
//                    //                   tyreAirPressure += b + ",";
//                } else if (mark.equals("7c84") && i == 1) {
//                    resultMap.put("rfPressure", b);
////                   tyreAirPressure += b + ",";
//                } else if (mark.equals("7c84") && i == 2) {
//                    resultMap.put("lrPressure", b);
////                   tyreAirPressure += b + ",";
//                } else if (mark.equals("7c84") && i == 3) {
//                    resultMap.put("rrPressure", b);
////                  tyreAirPressure += b + ",";
//                } else {
////                   System.out.println(partInfo.getName()+" "+b);
//                    resultMap.put(partInfo.getName(), b);
//                }
//            }
////            if (mark.equals("7c84")) {
////                tyreAirPressure = tyreAirPressure.substring(0, tyreAirPressure.length() - 1);
////                resultMap.put("tyreAirPressure", tyreAirPressure);
////            }
//        }
//        if (resultMap.get("avgFuelConsumed") != null) {
//            resultMap.put("avgFuelConsumed", new BigDecimal(resultMap.get("avgFuelConsumed").toString()).divide(new BigDecimal("100000"), 2));
//        }
//        if (resultMap.get("temperatureUsable") != null) {
//            if (resultMap.get("temperatureUsable").toString().equals("1")) {//车外温度是无效时
//                resultMap.put("ambientTemperature", 0);
//            } else {
//                Integer temp = Integer.parseInt(resultMap.get("ambientTemperature").toString());
//                Integer tepr = temp / 10 - 40;
//                resultMap.put("ambientTemperature", tepr);
//            }
//        }
//        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
////            System.out.println(entry.getKey() + "========" + entry.getValue());
//            // System.out.println(entry.getKey()+"-----------"+entry.getValue());
//        }
//
//        /*String json = "{\"vseSysOffIoIc\":\"0\",\"epbSysStatIndReqIc\":\"0\",\"lfTyreTemperature\":\"-40\",\"rfTyreTemperature\":\"-40\",\"laTyreTemperature\":\"-40\",\"RaTyreTemperature\":\"-40\",\"avmVecColorSetSt\":\"0\",\"avmOpModCntSt\":\"0\",\"camDisMesReqIc\":\"0\",\"lkaStaIc\":\"0\",\"lkaLanDetSta\":\"0\",\"interventionSide\":\"0\",\"laSensLvlSetSt\":\"0\",\"ldwsWarnStySetSt\":\"0\",\"ldwSetSt\":\"0\",\"fwdCollsnWarnIc\":\"0\",\"fcwSetSt\":\"0\",\"fcwLvlSetSt\":\"0\",\"navCurrRoadType\":\"0\",\"navSpdLimStat\":\"0\",\"navSpdLim\":\"0\",\"stOfWcg\":\"0\",\"drStHtCntSt\":\"0\",\"prkBrkSwAtvGw\":\"0\",\"alcSetSt\":\"0\",\"tsrSetSt\":\"0\",\"elkRdpSetSt\":\"0\",\"spdLimWarmOfseSetSt\":\"0\",\"spdLimWarnSetSt\":\"0\",\"ialPwrMdCtrlSt\":\"0\",\"ialMdSetSt\":\"0\",\"ialRndmMdTmSetSt\":\"0\",\"ialColor1SetSt\":\"0\",\"ialColor2SetSt\":\"0\",\"ialColor3SetSt\":\"0\",\"ialColor4SetSt\":\"0\",\"ialColor5SetSt\":\"0\",\"ialColor6SetSt\":\"0\",\"ialColor7SetSt\":\"0\",\"ialColor8SetSt\":\"0\",\"trnsRvsSwStGw\":\"0\",\"electricStatus\":\"0\",\"electricValue\":\"12.2\",\"maiLgtSwGw\":\"0\",\"hzrdLgtSwAtvGw\":\"0\",\"accompCntStGw\":\"0\",\"accSysModeIc\":\"0\",\"crsDfltSpdSetSt\":\"30\",\"vehicleBrand\":\"53\",\"anThWaStGw\":\"0\",\"ddAjrSwAtvGw\":\"0\",\"pdAjrSwAtvGw\":\"0\",\"lsdAjrSwAtvGw\":\"0\",\"rsdAjrSwAtvGw\":\"0\",\"hdOpenIndOnGw\":\"0\",\"tdAjrSwAtvGw\":\"0\",\"psDoorOpenSwActGw\":\"0\",\"drDoorOpenSwActGw\":\"0\",\"rlDoorOpenSwActGw\":\"0\",\"rrDoorOpenSwActGw\":\"0\",\"drPwrWndCntSt\":\"0\",\"psPwrWndCntSt\":\"0\",\"rlPwrWndCntSt\":\"0\",\"rrPwrWndCntSt\":\"0\",\"pwrSrofCntSt\":\"0\",\"acCntTmpCntSt\":\"10.0\",\"acPwrModCntSt\":\"0\",\"acbLwLvlCntStGw\":\"0\",\"acbLwModCntStGw\":\"0\",\"plgMaxOpePosSst\":\"0\",\"plgPwOptCmdActGw\":\"0\",\"remCntrlPwrLftGtEblCmdStaGw\":\"0\",\"lkaLanDetStaRcvrSt\":\"0\",\"lkaLanDetStagW\":\"0\",\"interventionSideGw\":\"0\",\"lkaSta\":\"0\",\"tkOverReqToDrv\":\"0\",\"depaLanSdlO\":\"0\",\"ldwReq\":\"0\",\"laExtDisReq\":\"0\",\"tjALatCntlMd\":\"0\",\"drvStrTorqGW\":\"0.0\",\"transCntlTypeGw\":\"0\",\"transCntrlMdGw\":\"0\",\"flLvlPctGw\":\"0.0\",\"instFuelConsmpRateGw\":\"0.0\",\"s4d1RcvrSt\":\"0\",\"engTrqCntlFlrStGw\":\"0\",\"accPosGw\":\"0.0\",\"accHdwyStg\":\"0\",\"accDrvrSeltdSpd\":\"0\",\"rsmDspMsgReq\":\"0\",\"accTakeOverReq\":\"0\",\"accModeId\":\"0\",\"aebSetSt\":\"0\",\"aebActive\":\"0\",\"aebTagId\":\"0\",\"aebFault\":\"0\",\"forTarObjDeteSta\":\"0\",\"ebaLatDisWarn\":\"0\",\"fwdCollsnWarn\":\"0\",\"hhBmCntSta\":\"0\",\"elkSt\":\"0\",\"rdpSt\":\"0\",\"spdLimWarnReq\":\"0\",\"traSigReco\":\"0\",\"epsCurrStModeSSt\":\"0\",\"drvStrgTrqReqStaGw\":\"0\",\"hhBmCntReqGw\":\"0\",\"drvStrgTrqReqGw\":\"0\",\"epsCurrStModeSstGw\":\"0\",\"vseLatAccGw\":\"0.0\",\"vseAtvGw\":\"0\",\"vehDynYawRateGw\":\"0.0\",\"vseLongAccGw\":\"0.0\",\"espMcBrPressureGw\":\"0.0\",\"longDistAccTrgId1Gw\":\"0.0\",\"vlcShtdwnMd\":\"0\",\"trgDecelValForAebEba\":\"-7.0\",\"accSysMode\":\"0\",\"accBrkPrefRq\":\"0\",\"accSmoBrkRq\":\"0\",\"aebCarReq\":\"0\",\"drvOffReq\":\"0\",\"deteTrgToStop\":\"0\",\"abaSensLvl\":\"0\",\"abaJekReq\":\"0\",\"awbJekReq\":\"0\",\"awbJekSensLvl\":\"0\",\"prefillReq\":\"0\",\"crsCntlMd\":\"0\",\"crsCntlMdv\":\"0\",\"aebVruReq\":\"0\",\"altitude\":\"0\",\"ccExtMsgReq\":\"0\",\"breakStatusEms\":\"0\",\"projectId\":\"wlapp\",\"vin\":\"V866793030084423\",\"serviceType\":\"9\",\"protocolVersion\":\"16\",\"msgType\":\"42\",\"messageProtocolVersion\":\"32\",\"messageID\":\"4\",\"hourDate\":\"1574735198000\",\"equipmentIDType\":\"0\",\"equipmentID\":\"P017001030000118\",\"tPdsn\":\"P866793030084423\",\"ngtpCreateTime\":\"2019-11-26 10:26:02\",\"teau\":\"-40\",\"nivHuile\":\"0\",\"autonomie\":\"0\",\"kmTotal\":\"0.0\",\"createdTimstm\":\"2019-11-26 10:26:38\",\"dbtype\":\"D\",\"currentSpeed\":\"0\",\"lon\":\"121.440076\",\"lat\":\"31.193070\",\"engineState\":\"\",\"eachMileage\":\"0.0\",\"avgFuelConsumed\":\"0\",\"tyreAirPressure\":\"0.0,0.0,0.0,0.0\",\"alerts\":\"alertType:alertValue|23:1\",\"tboxSatelliteNumbers\":\"0\",\"engineSpeed\":\"1\",\"gpsStatus\":\"1\",\"gpsTime\":\"2001-01-01 08:00:00\",\"networkUse\":\"1\",\"rssi\":\"-79\",\"vehicleState\":\"2\",\"ignition\":\"\",\"flameout\":\"\",\"horizontalLongitudeFactor\":\"0.0\",\"triaxialAccelerationX\":\"0.0\",\"triaxialAccelerationY\":\"0.0\",\"triaxialAccelerationZ\":\"0.0\",\"tboxNetworkBand\":\"BAND_E_UTRA_OPERATING_BAND_3\",\"cellularNetworkCountryCode\":\"460\",\"cellularNetworkCode\":\"03\",\"regionalAreaCode\":\"0\",\"baseStationCode\":\"4992\",\"gearboxPosition\":\"0\",\"speedReceiveStatus\":\"1\",\"throttleOpeningDegree\":\"0.0\",\"steeringAngle\":\"0.0\",\"steeringSpeed\":\"0\",\"steeringDirection\":\"0\",\"cruiseControlSign\":\"0\",\"cruiseControlTargetSpeed\":\"0\",\"ambientTemperature\":\"-40.0\",\"airconditionerPowerSwitch\":\"0\",\"airconditionerAutoSwitch\":\"0\",\"airconditionerLoopMode\":\"0\",\"airconditionerTargetTemperature\":\"10.0\",\"seatBeltMaster\":\"0\",\"seatBeltSlave\":\"0\",\"leftTurnSignal\":\"0\",\"rightTurnSignal\":\"0\",\"sideLightStatus\":\"0\",\"lowLightStatus\":\"0\",\"highLightStatus\":\"0\",\"frontFogLight\":\"0\",\"rearFogLight\":\"0\",\"wiperGear\":\"0\",\"lfPressureWarnning\":\"0\",\"rfPressureWarnning\":\"0\",\"lrPressureWarnning\":\"0\",\"rrPressureWarnning\":\"0\",\"tirePressureSensorStatus\":\"0\",\"realEngineState\":\"2\",\"createtime\":\"1574735203407\"}";
//        Map<String,Object> checkMap = (Map) ConvertUtils.parseJson(json,HashMap.class);
//        for(Map.Entry<String,Object> entry:checkMap.entrySet()){
//            if(!entry.getValue().equals(resultMap.get(entry.getKey()))){
//                System.out.println(entry.getKey()+":"+entry.getValue()+"========"+resultMap.get(entry.getKey()));
//            }
//           // System.out.println(entry.getKey()+"-----------"+entry.getValue());
//        }*/
//        //  System.out.println(resultMap.get("gpsTime"));
//        //exportCsv(resultMap,"d:\\2.csv");
//        return resultMap;
//    }
//
//
//    public void cleanup() {
//
//    }
//
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("vin", "busiTime", "parsedMessage"));
//    }
//
//    @Override
//    public Map<String, Object> getComponentConfiguration() {
//        return null;
//    }
//
//
//    public static void main(String[] args) {
////        TerminalDataDao partInfoDao = (TerminalDataDao) SpringContext.getInstance().getBean(TerminalDataDao.class);
////        try {
////            List<PartInfo> partInfos = partInfoDao.queryAll(PartInfo.class, "select * from botai_partinfo");
////            Map<String, List<PartInfo>> markToPartInfos = new LinkedHashMap<>();
////            for (PartInfo partInfo : partInfos) {
////                String mark = partInfo.getMark();
////                List<PartInfo> subList = null;
////                if (markToPartInfos.containsKey(mark) == false) {
////                    subList = new ArrayList<>();
////                    markToPartInfos.put(mark, subList);
////                } else {
////                    subList = markToPartInfos.get(mark);
////                }
////                subList.add(partInfo);
////            }
////
////
////            String message = "01107e014c5a574144414741374b423734383635317e02503836363739333033313339363436317e03007e0408407e05097e062a7e074f0b3d6a7e08207e090001517e107d014f0b3d537d027c05065bc9ec0195318e7c0800006cac7c0900de7c0b647c0e207c107c11027c1202007c1364587c1b000005547c1d007c240000007c2700047ffd7c290004427ffefb7c3a052f7c40000120167c57007c7000007c710000000000000000000000007c727c737b017b027b0300007b04ffffffff7c74007c75001b01007c76009b9472007c770002ba3e0000017c780000001e7c79028a017c80000002014a01014a017c8101017c820000010100000003007c83007c84000007a70000078c000007a7000007a77c85000000007c86007c87007c88007c89070705057c8a007c8b007c8c007c8d0000000000007c8e0000007c8f0000007c90007c91007c92007c93007c94007c95007c96007c97007c98007c99007c9a00000000000000000000007c9b007c9c007c9d0005a57c9e00357c9f017ca1000000000000000000007ca221000000ff";
////            Map dataMap = ProcessorManager.receive(message, markToPartInfos);
////         /*   String message = "01107e014c5a574144414741344b473130353034367e02503836363739333033303534323639337e03007e04006c7e05097e062a7e074ef727a47e08207e090001ce7e107d014ef727a47d027c0506857fff460173291f7c08000009607c0900cf7c0b677c0e2d7c107c11027c1202007c1301af7c1b000009647c1d067c240000007c2700005e7c29004ef6a7a57c3a03287c40000195467c57017c7000007c710000000000000000000000007c7242414e445f455f555452415f4f5045524154494e475f42414e445f337c737b013436307b0230337b0300007b0400001ec17c74017c75000000017c76000000000000007c77000271000000000000007c7800ff001e0000000000007c79028a017c80010001012c02012c0200007c8100017c820000010100000003007c83007c840000090e000008bb000008bb000008687c85000000007c86007c87007c88007c8913120e0e7c8a007c8b007c8c007c8d0000000000000000000000000000007c8e0001010000007c8f0000007c90007c91007c92007c93010000007c9400007c9500007c960000007c97007c9800007c990000000000007c9a00000000000000000000007c9b0000007c9c0000007c9d00058b7c9e00377c9f017ca1000000000000010101017ca27ffd7ffd7ffd7ffd007ca3000000007ca400007ca5000000000000000000000000007ca6007ca70000000000000000000000000000000000";
////            message = "01107e014c5a574145414741394b423939373534197e02503836363739333033303831313738227e03007e04001b7e05097e062a7e074ef508aa7e08207e090001d17e107d014ef5089b7d027c050714004d01e91bed7c08000001cc7c09013d7c0b767c0e347c100a0d14177c11027c1202007c1301bf7c1b000000047c1d077c240000007c2700fffd7c29004ef4889c7c3a06327c40000173187c57007c7000007c710000000000000000000000007c7242414e445f455f555452415f4f5045524154494e475f42414e445f337c737b013436307b0230337b0300007b04000081247c74017c75000900017c76000000000000007c7700488bc70000010000007c7800ff071e00000e0007007c79028a017c8000000200aa0100aa0100007c8100017c820000000000000000007c83027c8400000dbf00000693000007390000071d7c85070707077c86017c87007c88007c897ffd7ffe7ffe7ffe7c8a007c8b007c8c007c8d0400000202020100000400000000007c8e0001010000007c8f0000007c90007c91007c92007c93010000017c9401017c9502007c960000007c97f77c9802007c990200000000007c9a00000000000000000000007c9b0000007c9c0000007c9d0005897c9e00347c9f017ca1000000000000000000007ca200010000647ca3000000007ca400007ca5000000000000000000000000007ca6007ca70000000000000000000000000000000000";
////            Map dataMap = ProcessorManager.receive(message);*/
////        } catch (Exception e) {
////            logger.error("query botai_partinfo error", e);
////        }
//    }
//
//
//}
