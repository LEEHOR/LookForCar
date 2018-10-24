package com.cyht.wykc.mvp.modles.bean;



import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Car extends DataSupport implements Serializable {
	private String id;
	private String code;
	private String name;
	private String logo;
//	private String type;
//	private boolean isSelect;

	public Car() {
	}



	public Car(String id, String name, String code, String logo) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.logo = logo;

	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	//	public boolean isSelect() {
//		return isSelect;
// 	}
//	public void setIsSelect(boolean isSelect) {
//		this.isSelect = isSelect;
//	}


	/*public static final Creator<Car> CREATOR = new Creator<Car>(){

		@Override
		public Car createFromParcel(Parcel source) {
			Car car = new Car();
			car.id = source.readString();
			car.name = source.readString();
			car.code = source.readString();
			car.logo = source.readString();
//			city.isSelect = source.readByte() != 0;
			return car;
		}

		@Override
		public Car[] newArray(int size) {
			return new Car[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(code);
		dest.writeString(logo);
//		dest.writeByte((byte) (isSelect ? 1 : 0));
	}*/
}
