package com.miproyecto.Servlet;

import com.miproyecto.Usuario.Conexion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.*;
import java.sql.*;

public class ExcelExportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String modulo = request.getParameter("modulo"); // Ej: clientes, productos

        if (modulo == null || modulo.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Debe especificar un módulo.");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + modulo + ".xlsx");

        try (
            Workbook workbook = new XSSFWorkbook();
            Connection con = Conexion.getConexion()
        ) {
            Sheet hoja = workbook.createSheet("Datos");
            PreparedStatement ps;
            Row header = hoja.createRow(0);

            // Selección de consulta y cabecera según el módulo
            switch (modulo.toLowerCase()) {
                case "clientes":
                    crearEncabezado(header, "ID", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección", "Ciudad");
                    ps = con.prepareStatement("SELECT id_cliente, nombre, apellido, correo, telefono, direccion, ciudad FROM clientes");
                    break;
                case "productos":
                    crearEncabezado(header, "ID", "Nombre", "Categoría", "Cantidad", "Fecha Vencimiento", "Marca", "Precio Unitario");
                    ps = con.prepareStatement("SELECT id_producto, nombre, categoria, cantidad, fecha_vencimiento, marca, precio_unitario FROM productos");
                    break;
                case "inventarios":
                    crearEncabezado(header, "ID", "Nombre", "Categoría", "Cantidad Stock", "Fecha Ingreso");
                    ps = con.prepareStatement("SELECT id_inventario, nombre, categoria, cantidad_stock, fecha_ingreso FROM inventarios");
                    break;
                case "pedidos":
                    crearEncabezado(header, "ID Pedido", "ID Cliente", "ID Producto", "Cantidad", "Estado");
                    ps = con.prepareStatement("SELECT id_pedido, id_cliente, id_producto, cantidad, estado FROM pedidos");
                    break;
                case "almacen":
                    crearEncabezado(header, "ID", "Nombre", "Teléfono", "Dirección", "Ciudad");
                    ps = con.prepareStatement("SELECT id_almacen, nombre, telefono, direccion, ciudad FROM almacen");
                    break;
                case "ruta":
                    crearEncabezado(header, "ID", "Origen", "Destino", "Tiempo Estimado", "Tipo Vehículo", "Estado");
                    ps = con.prepareStatement("SELECT id_ruta, origen, destino, tiempo_estimado, tipo_vehiculo, estado FROM ruta");
                    break;
                case "transporte":
                    crearEncabezado(header, "ID", "Vehículo", "ID Ruta", "Estado", "Fecha Salida", "Fecha Llegada");
                    ps = con.prepareStatement("SELECT id_transporte, vehiculo, id_ruta, estado, fecha_salida, fecha_llegada FROM transporte");
                    break;
                case "carga":
                    crearEncabezado(header, "ID", "ID Transporte", "Descripción", "Peso", "Estado");
                    ps = con.prepareStatement("SELECT id_carga, id_transporte, descripcion, peso, estado FROM carga");
                    break;
                case "entregas":
                    crearEncabezado(header, "ID", "ID Pedido", "ID Transporte", "Fecha Salida", "Fecha Llegada", "Estado");
                    ps = con.prepareStatement("SELECT id_entrega, id_pedido, id_transporte, fecha_salida, fecha_llegada, estado FROM entregas");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Módulo no válido.");
                    return;
            }

            // Ejecutar la consulta
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                int rowNum = 1;

                while (rs.next()) {
                    Row fila = hoja.createRow(rowNum++);
                    for (int i = 1; i <= cols; i++) {
                        fila.createCell(i - 1).setCellValue(rs.getString(i));
                    }
                }

                // Autoajuste de columnas
                for (int i = 0; i < cols; i++) {
                    hoja.autoSizeColumn(i);
                }

                // Escribir el Excel en la salida del servlet
                try (OutputStream out = response.getOutputStream()) {
                    workbook.write(out);
                    out.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el Excel.");
        }
    }

    // Método auxiliar para crear encabezado
    private void crearEncabezado(Row row, String... columnas) {
        for (int i = 0; i < columnas.length; i++) {
            row.createCell(i).setCellValue(columnas[i]);
        }
    }
}
