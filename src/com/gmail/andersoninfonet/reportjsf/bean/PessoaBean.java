package com.gmail.andersoninfonet.reportjsf.bean;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.gmail.andersoninfonet.reportjsf.modelo.Pessoa;

@ManagedBean
@RequestScoped
public class PessoaBean {

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	public List<Pessoa> getPessoas() {
		
		Pessoa pe = new Pessoa();
		pe.setNome("Eduardo");
		pe.setApelido("Dudu");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 3, 19);
		pe.setDataNascimento(cal.getTime());
		pessoas.add(pe);
		
		pe = new Pessoa();
		pe.setNome("Nubia");
		pe.setApelido("Binha");
		
		cal = Calendar.getInstance();
		cal.set(2010, 5, 29);
		pe.setDataNascimento(cal.getTime());
		pessoas.add(pe);
		
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	
	public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException{
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Leaf_Green.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),null, new JRBeanCollectionDataSource(this.getPessoas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		stream.flush();
		stream.close();
		
		FacesContext.getCurrentInstance().responseComplete();			
	}
	
	public void verPDF(ActionEvent actionEvent) throws Exception{
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Leaf_Green.jasper"));		
		
		byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getPessoas()));
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream outStream = response.getOutputStream();
		outStream.write(bytes, 0 , bytes.length);
		outStream.flush();
		outStream.close();
			
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void exportarExcel(ActionEvent actionEvent) throws JRException, IOException{
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Leaf_Green.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),null, new JRBeanCollectionDataSource(this.getPessoas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReport.xlsx");
		ServletOutputStream stream = response.getOutputStream();
		
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();
		
		stream.flush();
		stream.close();
		
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void exportarWord(ActionEvent actionEvent) throws JRException, IOException{
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Leaf_Green.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),null, new JRBeanCollectionDataSource(this.getPessoas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReport.docx");
		ServletOutputStream stream = response.getOutputStream();
		
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();
		
		stream.flush();
		stream.close();
		
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void exportarPPoint(ActionEvent actionEvent) throws JRException, IOException{
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Leaf_Green.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),null, new JRBeanCollectionDataSource(this.getPessoas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReport.pptx");
		ServletOutputStream stream = response.getOutputStream();
		
		JRPptxExporter exporter = new JRPptxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();
		
		stream.flush();
		stream.close();
		
		FacesContext.getCurrentInstance().responseComplete();
	}
}
