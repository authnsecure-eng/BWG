package com.pcmc.bwg.controller;

import com.pcmc.bwg.entity.Beat;
import com.pcmc.bwg.service.AbstractNamedChildMasterService;
import com.pcmc.bwg.service.BeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/masters/beats")
public class BeatController extends AbstractNamedChildMasterController<Beat> {

    private final BeatService beatService;

    public BeatController(BeatService beatService) {
        this.beatService = beatService;
    }

    @Override
    protected AbstractNamedChildMasterService<Beat> service() {
        return beatService;
    }
}
