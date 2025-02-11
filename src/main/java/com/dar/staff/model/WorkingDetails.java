package com.dar.staff.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_workingDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String project;
	private Date date;
	private String particulars;
	private String unit;
	private String lessons;
	private String outcome;
	private String hrs;
	private String num;
	private String status;
	private String url;
	
	 // Many-to-One with UserInfo (Many working details can belong to one user)
    @ManyToOne
    private UserInfo userInfo;

    // Many-to-One with Department (Many working details can belong to one department)
    @ManyToOne
    private Department department;
    
    @Transient
    private Long userId;
    @Transient
    private Long departmentId;
}
