package pe.com.bn.mgtt.persistence.dto.internal;

 import java.util.List;

import lombok.Data;

@Data
public class DatosTarjetaCliente {

	private Cliente cliente;
	private Tarjeta tarjeta;
	private EstadoTarjeta estadoTarjeta;
	private List<Tarjeta> tarjetas;
}
