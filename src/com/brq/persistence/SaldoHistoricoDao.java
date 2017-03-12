package com.brq.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.brq.model.Conta;
import com.brq.model.SaldoHistorico;

public class SaldoHistoricoDao extends Dao {

	public List<SaldoHistorico> buscar(Conta c) throws Exception {
		open();
		List<SaldoHistorico> lista = new ArrayList<SaldoHistorico>();
		
		stmt = con.prepareStatement("select * from (select idconta, idagencia, trunc(data) data, dense_rank() over (partition by trunc(data), idconta, idagencia order by data desc) rk, saldo from saldohistorico) h where rk = 1 and idconta = ? and idagencia = ?");
		stmt.setInt(1, c.getIdconta());
		stmt.setInt(2, c.getAgencia().getIdagencia());
		rs = stmt.executeQuery();
		while (rs.next()) {
			SaldoHistorico sh = new SaldoHistorico(c, rs.getDate("data"), rs.getDouble("saldo"));
			lista.add(sh);
		}
		
		close();
		return lista;
	}
}
