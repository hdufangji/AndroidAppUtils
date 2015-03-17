package com.victor_fun.android_app_utils.fundation_technoligy.java;

public class EnumDemon {
	enum Money{
		PENNY, QUARTY, TEN
	}
	
	private Money m;
	public EnumDemon(Money m){
		this.m = m;
	}

	public static void main(String[] args) {
		EnumDemon ed = new EnumDemon(Money.PENNY);
		System.out.println(ed);
		
		for(Money m : Money.values()){
			System.out.println(m);
		}
	}

}
