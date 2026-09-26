package com.pcmc.bwg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "beats")
public class Beat extends NamedChildMasterEntity {
}
