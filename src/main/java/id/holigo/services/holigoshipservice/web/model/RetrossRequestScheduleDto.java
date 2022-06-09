package id.holigo.services.holigoshipservice.web.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetrossRequestScheduleDto implements Serializable {

    private String rqid;

    private String mmid;

    private String app;

    private String action;

    private String org;

    private String des;

    private String trip;

    private String tgl_dep;

    private Integer adt_m;

    private Integer adt_f;

    private Integer inf;
}
