package com.lpf.mysuperdemo.bean;

import java.util.ArrayList;
import java.util.List;

public class StarProductList {

	private String starproducttitle;
	private List<StarProduct> starproductlist = new ArrayList<StarProduct>();
	
	public String getStarproducttitle() {
		return starproducttitle;
	}
	public void setStarproducttitle(String starproducttitle) {
		this.starproducttitle = starproducttitle;
	}
	public List<StarProduct> getStarproductlist() {
		return starproductlist;
	}
	public void setStarproductlist(List<StarProduct> starproductlist) {
		this.starproductlist = starproductlist;
	}
	
	
}
