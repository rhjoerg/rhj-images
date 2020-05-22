package ch.rhj.images.svg.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonTypeName("rect")
public class Rect {

	@JacksonXmlProperty(isAttribute = true)
	public double x;
	@JacksonXmlProperty(isAttribute = true)
	public double y;

	@JacksonXmlProperty(isAttribute = true)
	public double width;
	@JacksonXmlProperty(isAttribute = true)
	public double height;

	@JacksonXmlProperty(isAttribute = true)
	public double rx;
	@JacksonXmlProperty(isAttribute = true)
	public double ry;

	@JacksonXmlProperty(isAttribute = true)
	public String fill;

	protected Rect() {
	}

	public Rect(double x, double y, double width, double height, double rx, double ry, String fill) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rx = rx;
		this.ry = ry;
		this.fill = fill;
	}
}
