package com.pcmc.bwg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bwg_sub_categories")
public class BwgSubCategory extends NamedChildMasterEntity {
}
