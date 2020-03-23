/*
 * Historial de Modificaciones
 * ---------------------------------------------------------------------------------------------------------------------
 * Autor        Fecha       Descripción
 * ------------ ----------- --------------------------------------------------------------------------------------------
 * B153321      07/DIC/2016 Creación de la clase.
 * ------------ ----------- --------------------------------------------------------------------------------------------
 * FVILLA       21/MAR/2017 Se quita la anotación autowired y se reemplaza por resource. Se revisa y da formato a la
 *                          clase.
 * ------------ ----------- --------------------------------------------------------------------------------------------
 * FVILLA       24/ENE/2018 Se crea el método prorratearPedidos que será el puente para que los sistemas satélites
 *                          realicen el prorrateo.
 * ------------ ----------- --------------------------------------------------------------------------------------------
 * FVILLA       27/ENE/2020 Se asigna el identificador del servicio a los métodos para su identificación.
 */
package mx.com.bancoazteca.scl.boot.controllers.bdmprorrateo;

import mx.com.bancoazteca.scl.boot.beans.bdmprorrateo.DatosEntradaProrrateo;
import mx.com.bancoazteca.scl.boot.beans.bdmprorrateo.DatosSalidaProrrateo;
import mx.com.bancoazteca.scl.boot.business.bdmprorrateo.ProrrateoBO;
import mx.com.bancoazteca.scl.boot.catalogos.SrcService;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;

/**
 * Controlador de los servicios para realizar el prorrateo de los pedidos de cr&eacute;dito para BAZ Digital y otros
 * sistemas sat&eacute;lites.
 * Controlador de los servicios para consultas y procesos del prorrateo de pedidos en la Banca Digital - M&oacute;vil.
 * Created by B153321 on 07/12/2016.
 */
@RestController
@SuppressWarnings("unused")
@RequestMapping("/prorrateo")
public class ProrrateoController {
    /**Logger*/
    private static final Logger LOG = LoggerFactory.getLogger( ProrrateoController.class );
    /**Capa de negocio del controlador*/
    @Resource( name = "prorrateoBO" )
    private ProrrateoBO bo;
    /**
     * Realiza el prorrateo de los pedidos de cr&eacute;dito solicitados.
     * @param dtaPrt Es el objeto que tiene todos los datos de entrada, necesarios para calcular el prorrateo.
     * @return <code>DatosSalidaProrrateo</code> Los pedidos prorrateados de acuerdo a lo solicitado.
     */
    @ApiMethod(
              id          = "PRT001"
            , path        = ".../prorrateo/calProrrateo"
            , verb        = ApiVerb.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , description = "Realiza el prorrateo de los pedidos de crédito solicitados."
    )
    @RequestMapping(
              value    = "/calProrrateo"
            , method   = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public DatosSalidaProrrateo calcularProrrateo( @RequestBody DatosEntradaProrrateo dtaPrt
    ) {
        long tini = System.currentTimeMillis();
        SrcService bdmSrv = SrcService.BDM_PRT_CALPRORRATEO;
        LOG.info( "{}[{}] - Inicia: {} {}"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), bdmSrv.getRutaSrv() );
        DatosSalidaProrrateo prtRsp = bo.calcularProrrateo( tini, dtaPrt );
        LOG.info( "{}[{}] - Termina:  (T. Total: {}) RSP: {}"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), (System.currentTimeMillis() - tini), prtRsp.getCodigo() );
        return prtRsp;
    }
    /**
     * Realiza el prorrateo de los pedidos de cr&eacute;dito solicitados.
     * @param depZip jSon con los pedidos a prorratear.
     * @return <code>DatosSalidaProrrateo</code> Los pedidos prorrateados de acuerdo a lo solicitado.
     */
    @ApiMethod(
              id          = "PRT002"
            , path        = ".../prorrateo/prorratear"
            , verb        = ApiVerb.POST
            , consumes    = MediaType.TEXT_PLAIN_VALUE
            , produces    = MediaType.TEXT_PLAIN_VALUE
            , description = "Realiza el prorrateo de los pedidos de crédito solicitados."
    )
    @RequestMapping(
              value    = "/prorratear"
            , method   = RequestMethod.POST
            , consumes = MediaType.TEXT_PLAIN_VALUE
            , produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String calcularProrrateoExternos( @RequestBody String depZip
    ) {
        long tini = System.currentTimeMillis();
        SrcService bdmSrv = SrcService.BDM_PRT_PRORRATEOEXT;
        LOG.info( "{}[{}] - Inicia: {} {}"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), bdmSrv.getRutaSrv() );
        String prtRsp = bo.calcularProrrateoExternos( tini, depZip );
        LOG.info( "{}[{}] - Termina:  (T. Total: {})"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), (System.currentTimeMillis() - tini) );
        return prtRsp;
    }
    /**
     * Realiza el prorrateo de los pedidos de cr&eacute;dito solicitados.
     * @param dtaPrt Es el objeto que tiene todos los datos de entrada, necesarios para calcular el prorrateo.
     * @return <code>DatosSalidaProrrateo</code> Los pedidos prorrateados de acuerdo a lo solicitado.
     */
    @ApiMethod(
            id          = "PRT003"
            , path        = ".../prorrateo/"
            , verb        = ApiVerb.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , description = "Realiza el prorrateo de los pedidos de crédito solicitados."
    )
    @RequestMapping(
            value    = "/"
            , method   = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public DatosSalidaProrrateo calcularProrrateoAbonoCredito( @RequestBody DatosEntradaProrrateo dtaPrt
    ) {
        long tini = System.currentTimeMillis();
        SrcService bdmSrv = SrcService.BDM_PRT_PRORRATEOABN;
        LOG.info( "{}[{}] - Inicia: {} {}"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), bdmSrv.getRutaSrv() );
        DatosSalidaProrrateo prtRsp = bo.prorratearAbonoCredito( tini, bdmSrv, dtaPrt );
        LOG.info( "{}[{}] - Termina:  (T. Total: {}) RSP: {}"
                , bdmSrv.getCveSrv(), tini, bdmSrv.getDescSrv(), (System.currentTimeMillis() - tini), prtRsp.getCodigo() );
        return prtRsp;
    }
}
