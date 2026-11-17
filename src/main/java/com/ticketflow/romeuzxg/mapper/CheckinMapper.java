package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Checkin;
import com.ticketflow.romeuzxg.domain.Ingresso;
import com.ticketflow.romeuzxg.dto.request.CheckinRequest;
import com.ticketflow.romeuzxg.dto.response.CheckinResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckinMapper {

    public Checkin toEntity(CheckinRequest req, Ingresso ingresso) {
        Checkin checkin = new Checkin();
        checkin.setFeitoEm(req.feitoEm());
        checkin.setIngresso(ingresso);
        checkin.setPortaria(req.portaria());
        return checkin;
    }

    public CheckinResponse toDTO(Checkin checkin) {
        return new CheckinResponse(
                checkin.getId(), checkin.getIngresso(), checkin.getFeitoEm(), checkin.getPortaria()
        );
    }

}
