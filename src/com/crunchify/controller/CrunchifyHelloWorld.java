package com.crunchify.controller;
 
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
 
/*
 * author: Crunchify.com
 * 
 */
 
@Controller
public class CrunchifyHelloWorld {
 
	 @Autowired  
	    Comparator<String> comparator;
	
	@RequestMapping("/")
	public String helloWorld() {
 
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
	//	return new ModelAndView("welcome", "message", message);
		return "welcome";
	}
	
	  @RequestMapping(value = "/welcome", method = RequestMethod.POST)
	  public String compare(@RequestParam("input1") String input1,  
	            @RequestParam("input2") String input2, Model model) throws FileNotFoundException {  
	   
	        int result = comparator.compare(input1, input2);  
	        String inEnglish = (result < 0) ? "less than" : (result > 0 ? "greater than" : "equal to");  
	   
	        String output = "According to our Comparator, '" + input1 + "' is " + inEnglish + "'" + input2 + "'";  
	   
	        model.addAttribute("output", output); 
	        
	        try {
	        	Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream("/usr/local/createSamplePDF.pdf"));
				FileOutputStream fos = new FileOutputStream("/usr/local/createSamplePDF.pdf");
				int index = 1; // 表格序号  
	            int page = 2;// 总共页数
				ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];
				String TemplatePDF ="/usr/local/f1040nre.pdf";
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader,fos);   
				AcroFields form = stamp.getAcroFields();
				 Map<String, Item> fieldMap = form.getFields();
				 for (Entry<String, Item> entry : fieldMap.entrySet()) {  
				        String name = entry.getKey(); // name就是pdf模版中各个文本域的名字  
				        Item item = (Item) entry.getValue();  
				        System.out.println("[name]:" + name + ", [value]: " + item);  
				    } 
				 form.setField("f1_01_0_", input1);  
				    form.setField("f1_02_0_", input2);  
				    form.setField("f1_05_0_", "我是联系人123");  
				  
				    stamp.setFormFlattening(true); // 这句不能少 
				    stamp.close();
				    reader.close();
	        document.open(); 
	        document.add(new Paragraph(input2));
	        document.close();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	        
	        return "compareResult";  
	    }
	
}