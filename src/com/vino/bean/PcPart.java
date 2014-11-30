package com.vino.bean;

public class PcPart {
	private int part_pk;
	private String part_title;
	private int part_code;
	private String part_maker;
	private int part_count;
	private String part_desc;

	public PcPart(int part_pk, String part_title, int part_code,
			String part_maker, int part_count, String part_desc) {
		super();
		this.part_pk = part_pk;
		this.part_title = part_title;
		this.part_code = part_code;
		this.part_maker = part_maker;
		this.part_count = part_count;
		this.part_desc = part_desc;
	}

	public int getPart_pk() {
		return part_pk;
	}

	public void setPart_pk(int part_pk) {
		this.part_pk = part_pk;
	}

	public String getPart_title() {
		return part_title;
	}

	public void setPart_title(String part_title) {
		this.part_title = part_title;
	}

	public int getPart_code() {
		return part_code;
	}

	public void setPart_code(int part_code) {
		this.part_code = part_code;
	}

	public String getPart_maker() {
		return part_maker;
	}

	public void setPart_maker(String part_maker) {
		this.part_maker = part_maker;
	}

	public int getPart_count() {
		return part_count;
	}

	public void setPart_count(int part_count) {
		this.part_count = part_count;
	}

	public String getPart_desc() {
		return part_desc;
	}

	public void setPart_desc(String part_desc) {
		this.part_desc = part_desc;
	}
	
	
}
