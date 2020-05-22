package ch.rhj.images.svg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonTypeName("circle")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Circle {

	@JacksonXmlProperty(isAttribute = true)
	public double cx;

	@JacksonXmlProperty(isAttribute = true)
	public double cy;

	@JacksonXmlProperty(isAttribute = true)
	public double r;

	@JacksonXmlProperty(isAttribute = true)
	public String fill;

	protected Circle() {
	}

	public Circle(double cx, double cy, double r, String fill) {

		this.cx = cx;
		this.cy = cy;
		this.r = r;
		this.fill = fill;
	}
}
