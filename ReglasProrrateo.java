package mx.com.baz.investigacion.cobranza.controllers.brms;

import mx.com.baz.investigacion.cobranza.beans.brms.RespuestaProrrateo;
import mx.com.baz.investigacion.cobranza.business.brms.ReglasProrrateoBO;
import mx.com.baz.investigacion.cobranza.catalogos.SrcService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Creado por gdominguezm 13/03/2020.
 */

@Controller ("reglasProrrateoService")
@RequestMapping("/brms")
@Api(
        name        = "ReglasProrrateoService"
        , group       = "BRMS"
        , description = "Controlador para la consulta de reglas de  prorrateo"
)

public class ReglasProrrateoService implements Serializable {

    /** Logger*/
    private static final Logger LOG = LoggerFactory.getLogger( ReglasProrrateoService.class );
    /**Capa de negocio del controlador*/
    @Resource( name = "reglasProrrateoBO" )
    private ReglasProrrateoBO bo;
    /**
     * Servicio para la consulta de descarga de reglas de Prorrateo del BRMS.
     * @param tipoProrrateo Tipo de cartera.
     * @return <code>RespuestaProrrateo</code> Respuesta con la informaci&oacute;n de las reglas de Prorrateo.
     */

    @ApiMethod(
            id          = "RBMS003"
            , path        = ".../brms/reglasProrrateo?tipoProrrateo={tipoProrrateo}"
            , verb        = ApiVerb.GET
            , produces    = MediaType.APPLICATION_JSON_VALUE
            , description = "Consulta el BRMS para obtener las reglas de Prorrateo"
    )
    @RequestMapping(
            value    = "/reglasProrrateo"
            , method   = RequestMethod.GET
            , produces = "application/json"
    )
    @ResponseBody
    public RespuestaProrrateo consultarReglasProrrateo(
            @ApiQueryParam( name = "tipoProrrateo", description = "Tipo de Prorrateo." ) @RequestParam( value = "tipoProrrateo" , required = false ) Integer tipoProrrateo
    ) {
        long tini = System.currentTimeMillis();
        SrcService cdmSrv = SrcService.SRV_BRMS_CAMPANAS_CART; //modificar por constante de descarga de reglas de prorrateo
        LOG.info( "{}[{}] - Inicia: {} {}?tipoProrrateo={}"
                , cdmSrv.getCveSrv(), tini, cdmSrv.getDescSrv(), cdmSrv.getRutaSrv(), tipoProrrateo);
        RespuestaProrrateo svrRsp = bo.consultarReglasBRMS( tini, cdmSrv,tipoProrrateo );
        LOG.debug( "{}[{}] - Consultar reglas de Prorrateo. RSP: {}", cdmSrv.getCveSrv(), tini, svrRsp );
        LOG.info( "{}[{}] - Termina: {} (T. Exec: {} ms). RSP: {}"
                , cdmSrv.getCveSrv(), tini, cdmSrv.getDescSrv(), (System.currentTimeMillis() - tini) );//, svrRsp.getMensaje()

        return svrRsp;
    }


}
