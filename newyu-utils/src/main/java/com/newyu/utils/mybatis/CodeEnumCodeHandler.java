/** 
 * Project Name:easydata.commons 
 * File Name:CodeEnumCodeHandler.java 
 * Package Name:com.ez.commons.mybatis 
 * Date:2017年3月25日下午12:43:03 
 * Copyright (c) 2017, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.utils.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: CodeEnumCodeHandler <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月25日 下午12:43:03 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class CodeEnumCodeHandler extends BaseTypeHandler<GenericEnum> {
	private final GenericEnum[] enums;

	public CodeEnumCodeHandler(Class<GenericEnum> type) {
		if (type == null){
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.enums = type.getEnumConstants();
		if (this.enums == null) {
			throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, GenericEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public GenericEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位EnumStatus子类
			return locateEnumStatus(i);
		}
	}

	@Override
	public GenericEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位EnumStatus子类
			return locateEnumStatus(i);
		}
	}

	@Override
	public GenericEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位EnumStatus子类
			return locateEnumStatus(i);
		}
	}

	/**
	 * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
	 * 
	 * @param code
	 *            数据库中存储的自定义code属性
	 * @return code对应的枚举类
	 */
	private GenericEnum locateEnumStatus(int code) {
		for (GenericEnum status : enums) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
}
