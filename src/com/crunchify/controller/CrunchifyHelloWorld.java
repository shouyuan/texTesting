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
	
	
	
	  @RequestMapping(value = "/welcome", method = RequestMethod.POST)
	  public String compare(@RequestParam("input1") String input1,  
	            @RequestParam("input2") String input2, Model model) throws FileNotFoundException {  
	   
	        int result = comparator.compare(input1, input2);  
	        String inEnglish = (result < 0) ? "less than" : (result > 0 ? "greater than" : "equal to");  
	   
	        String output = "According to our Comparator, '" + input1 + "' is " + inEnglish + "'" + input2 + "'";  
	   
	        model.addAttribute("output", output); 
	        
	        try {
	        	Document document = new Document();
	        
	    		int index = 1; 
	            int page = 2;
				ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];
				//String TemplatePDF ="f1040nre.pdf";
				Document aaa =new Document();
				PdfReader reader = new PdfReader("C:/Users/Sharon/workspace/texTesting/f1040nre.pdf");
				PdfStamper stamp = new PdfStamper(reader,new FileOutputStream(  
	    	            "C:/Users/Sharon/workspace/texTesting/f1040nre2.pdf")); 
				AcroFields form = stamp.getAcroFields();
				 Map<String, Item> fieldMap = form.getFields();
				 int i=0;
				 for (Entry<String, Item> entry : fieldMap.entrySet()) {  
				        String name = entry.getKey();  
				        Item item = (Item) entry.getValue();  
				        System.out.println("[name]:" + name + ", [type]: " + form.getFieldType(name));  
				        form.setField(name, i+++"");  
				    } 
				 
				   
				  
				    stamp.setFormFlattening(true); 
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