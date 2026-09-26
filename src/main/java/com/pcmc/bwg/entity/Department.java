package com.pcmc.bwg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department extends NamedMasterEntity {
}
