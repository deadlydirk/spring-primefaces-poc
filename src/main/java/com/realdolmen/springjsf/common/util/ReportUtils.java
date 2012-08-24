package com.realdolmen.springjsf.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ReportUtils {

	public static final String COMPILED_REPORTS_DIRECTORY = "/reports/";
	public static final String COMPILED_REPORTS_EXTENSION = ".jasper";
	public static final String UNCOMPILED_REPORTS_DIRECTORY = "/reports/uncompiled/";
	public static final String UNCOMPILED_REPORTS_EXTENSION = ".jrxml";
	public static final String PDF_CONTENT_TYPE = "application/pdf";
	public static final String PDF_EXTENSION = ".pdf";
	
	public static JasperReport getCompiledReport(String reportName) throws JRException,
			IOException {
		Resource resource = new ClassPathResource(COMPILED_REPORTS_DIRECTORY
				+ reportName + COMPILED_REPORTS_EXTENSION);
		return (JasperReport) JRLoader.loadObject(resource.getInputStream());
	}
	
	public static JasperReport getUnCompiledReport(String reportName) throws JRException, IOException {
		Resource resource = new ClassPathResource(UNCOMPILED_REPORTS_DIRECTORY
				+ reportName + UNCOMPILED_REPORTS_EXTENSION);
		JasperDesign jasperDesign = JRXmlLoader.load(resource.getFile());
		return JasperCompileManager.compileReport(jasperDesign);
	}
	
	public static InputStream createReport(String reportName, Map<String, Object> parameters, Collection<?> reportData) throws JRException, IOException {
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportUtils.getCompiledReport(reportName), parameters, new JRBeanCollectionDataSource(reportData));
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		return new ByteArrayInputStream(pdf);
	}
	
}
