package com.pcmc.bwg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "electoral_wards")
public class ElectoralWard extends NamedChildMasterEntity {
}
