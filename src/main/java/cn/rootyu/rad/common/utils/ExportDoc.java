package cn.rootyu.rad.common.utils;

/**
 * doc导出
 * @author lichuang
 *
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportDoc {  
  
    private Configuration configuration = null;  
  
    public ExportDoc() {  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("utf-8");  
    }  
  /**
   * 
   * @param dataMap 数据
   * @param fileName 输出文件路径
   * @throws IOException 
   */
    public void createDoc(Map<String,Object> dataMap,String fileName) throws IOException {  
        //dataMap 要填入模本的数据文件  
        configuration.setClassForTemplateLoading(this.getClass(), "/template");  
        //configuration.setDirectoryForTemplateLoading(new File("E:/workSpace/qmis_as/src/com/dhc/ims/common/template"));
    	Template template=null;  
        try {  
            //report.ftl为要装载的模板  
        	template = configuration.getTemplate("repairNotice.ftl");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //输出文档路径及名称  
        File outFile = new File(fileName);  
        Writer out = null;  
        FileOutputStream fos=null;  
        try {  
            fos = new FileOutputStream(outFile);  
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
            //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
             out = new BufferedWriter(oWriter);   
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
        	template.process(dataMap, out);  
            out.close();  
            fos.close();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    /**
     *
     * @param dataMap 数据
     * @param fileName 输出文件路径
     * @throws IOException
     */
    public void createDoc1(Map<String,Object> dataMap,String fileName) throws IOException {
        //dataMap 要填入模本的数据文件
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        //configuration.setDirectoryForTemplateLoading(new File("E:/workSpace/qmis_as/src/com/dhc/ims/common/template"));
        Template template=null;
        try {
            //report.ftl为要装载的模板
            template = configuration.getTemplate("aaa.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出文档路径及名称
        File outFile = new File(fileName);
        Writer out = null;
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(outFile);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
            //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out = new BufferedWriter(oWriter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            template.process(dataMap, out);
            out.close();
            fos.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param dataMap 数据
     * @param fileName 输出文件路径
     * @param templateName 模板名称
     * @param templatePath 模板路径
     * @throws UnsupportedEncodingException
     */
    public void createDoc(Map<String,Object> dataMap,String fileName,String templateName,String templatePath) throws UnsupportedEncodingException {  
        //dataMap 要填入模本的数据文件  
        //模板是放在templatePath包下面  
        configuration.setClassForTemplateLoading(this.getClass(), templatePath);  
        Template template=null;  
        try {  
            //report.ftl为要装载的模板  
        	template = configuration.getTemplate(templateName);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //输出文档路径及名称  
        File outFile = new File(fileName);  
        Writer out = null;  
        FileOutputStream fos=null;  
        try {  
            fos = new FileOutputStream(outFile);  
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
            //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
             out = new BufferedWriter(oWriter);   
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
        	template.process(dataMap, out);  
            out.close();  
            fos.close();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
    }  
    /**
     * 图片转码
     * @param fileList 图片列表，多个图片
     * @return
     */
    public static List<String> getImageStr(List<String> fileList) {  
    	List<String> list = new ArrayList<String>();
    	for(String imgFile : fileList){
    		InputStream in = null;  
    		byte[] data = null;  
    		try {  
    			in = new FileInputStream(imgFile);  
    			data = new byte[in.available()];  
    			in.read(data);  
    			in.close();  
    		} catch (IOException e) {  
    			e.printStackTrace();  
    		}  
    		BASE64Encoder encoder = new BASE64Encoder();  
    		String base64 = encoder.encode(data);
    		list.add(base64);
    	}
        return list;   
    } 
    /**
     * 单个图片转码
     * @param file
     * @return
     */
    public static String getImageStr(String file) {  
		InputStream in = null;  
		byte[] data = null;  
		try {  
			in = new FileInputStream(file);  
			data = new byte[in.available()];  
			in.read(data);  
			in.close();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);
    }  
     
   /*public static void main(String[] args) throws IOException {
	   Map<String, Object> map = new HashMap<String, Object>();  
	   Ncr wkMet = new Ncr();
	   wkMet.setNo("111");
	   wkMet.setUrgenFlag("1");
	   wkMet.setOppottunityName("类型1");
	   wkMet.setProjectName("项目1");
	   wkMet.setStationName("工位1");
	   wkMet.setPrdName("prdName");
	   wkMet.setSupplier("supplier");
	   wkMet.setFaultCode("faultCode");
	   wkMet.setNcrDesc("ncrDesc");
	   wkMet.setCreateBy("createBy");
	   wkMet.setCreateDate("2017-08-25".);
	   wkMet.setFaultClassName("faultClassName");
	   wkMet.setApproOpinion("approOpinion");
	   wkMet.setApproDate("2017-08-25");
	   wkMet.setLeaderOpinion("leaderOpinion");
	   wkMet.setApproUserName("approUserName");
	   wkMet.setApprovaDate("approvaDate");
	   wkMet.setQaOpinion("qaOpinion");
	   wkMet.setMeetingFalg("meetingFalg");
	   wkMet.setDisUserName("disUserName");
	   wkMet.setDisDate(disDate);
	   wkMet.setRatifyOpinion("ratifyOpinion");
	   wkMet.setRatifyUserName("ratifyUserName");
	   wkMet.setRatifyDate(ratifyDate);
	   wkMet.setCloseUserName("closeUserName");
	   wkMet.setCloseDate(closeDate);
       map.put("ncr",wkMet);
       List<String> list1 = new ArrayList<String>();
       list1.add("文件1");
       list1.add("文件2");
       list1.add("文件3");
       list1.add("文件4");
       map.put("fileNames", list1);
       List<String> list = new ArrayList<String>();
       list.add("E:/桌面图片/1b4c510fd9f9d72a7a751000d42a2834349bbb9d.jpg");
       list.add("E:/桌面图片/7c1ed21b0ef41bd5e6c559a057da81cb38db3dcb.jpg");
       list.add("E:/桌面图片/114677-94FUMv9.jpg");
       list.add("E:/桌面图片/902397dda144ad3464639dc8d1a20cf430ad85a4.jpg");
       list.add("E:/桌面图片/902397dda144ad3464639dc8d1a20cf430ad85a4.jpg");
       list.add("E:/桌面图片/902397dda144ad3464639dc8d1a20cf430ad85a4.jpg");
       map.put("images", getImageStr(list));
       map.put("fileName", "23");
       ExportDoc mdoc = new ExportDoc();  
       mdoc.createDoc(map, "E:/outFile.doc");  
   }*/
}  
