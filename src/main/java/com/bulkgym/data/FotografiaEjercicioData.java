	package com.bulkgym.data;
	
	import java.sql.Types;
	import java.util.List;
	import java.util.Map;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.jdbc.core.SqlOutParameter;
	import org.springframework.jdbc.core.SqlParameter;
	import org.springframework.jdbc.core.simple.SimpleJdbcCall;
	import org.springframework.stereotype.Repository;
	import org.springframework.transaction.annotation.Transactional;
	
	import com.bulkgym.domain.FotografiaEjercicio;
	
	@Repository
	public class FotografiaEjercicioData {
	
	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	
	    public List<FotografiaEjercicio> findAll() {
	        String sql = "SELECT id_fotografia, ruta_imagen, id_ejercicio FROM FotografiaEjercicio";
	        return jdbcTemplate.query(sql, new FotografiaExtractor());
	    }
	
	    @Transactional
	    public void guardar(FotografiaEjercicio fotografia) {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("dbo")
	                .withProcedureName("InsertFotografiaEjercicio")
	                .declareParameters(
	                        new SqlParameter("ruta_imagen", Types.VARCHAR),
	                        new SqlParameter("id_ejercicio", Types.INTEGER),
	                        new SqlOutParameter("id_fotografia", Types.INTEGER)
	                );
	
	        Map<String, Object> params = Map.of(
	            "ruta_imagen", fotografia.getRutaImagen(),
	            "id_ejercicio", fotografia.getIdEjercicio()
	        );
	
	        Map<String, Object> result = jdbcCall.execute(params);
	        fotografia.setIdFotografia((Integer) result.get("id_fotografia"));
	    }
	
	    public void delete(int idFotografia) {
	        String sql = "DELETE FROM FotografiaEjercicio WHERE id_fotografia = ?";
	        jdbcTemplate.update(sql, idFotografia);
	    }
	
	    public FotografiaEjercicio findById(int idFotografia) {
	        String sql = "SELECT id_fotografia, ruta_imagen, id_ejercicio FROM FotografiaEjercicio WHERE id_fotografia = ?";
	        List<FotografiaEjercicio> fotografias = jdbcTemplate.query(sql, new FotografiaExtractor(), idFotografia);
	        return fotografias.isEmpty() ? null : fotografias.get(0);
	    }
	    
	    // (Opcional) Si en el futuro quieres actualizar la ruta de una foto
	    public void update(FotografiaEjercicio fotografia) {
	        String sql = "UPDATE FotografiaEjercicio SET ruta_imagen = ?, id_ejercicio = ? WHERE id_fotografia = ?";
	        jdbcTemplate.update(sql, fotografia.getRutaImagen(), fotografia.getIdEjercicio(), fotografia.getIdFotografia());
	    }
	}
