package com.realdolmen.springjsf.domain;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MappedSuperclass
public abstract class AbstractEntity<K, V> {

	@Version
	private V version;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private K id;
	
	private Timestamp lastUpdateTimestamp;
	
	@PrePersist @PreUpdate
	private void onUpdate() {
		lastUpdateTimestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public K getId(){
		return id;
	}
	
	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	
	public V getVersion() {
		return version;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
