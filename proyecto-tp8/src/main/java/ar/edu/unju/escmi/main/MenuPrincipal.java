package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.DetalleFactura;
import ar.edu.unju.escmi.entities.Factura;
import ar.edu.unju.escmi.entities.Producto;
import jakarta.persistence.EntityManager;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Bienvenido al Menú Principal");
            System.out.println("Seleccione una opcion: ");
            System.out.println("1- Alta de Cliente");
            System.out.println("2- Alta de producto");
            System.out.println("3- Realizar la venta de productos (Alta de una nueva factura)");
            System.out.println("4- Buscar una factura ingresando su numero de factura y mostrar todos sus datos");
            System.out.println("5- Eliminar una factura (eliminacion logica, se usa el atributo estado)");
            System.out.println("6- Eliminar un producto (eliminacion logica, se usa el atributo estado)");
            System.out.println("7- Modificar datos de un cliente");
            System.out.println("8- Modificar precio de un producto");
            System.out.println("9- Mostrar todas las facturas");
            System.out.println("10- Mostrar todos los clientes");
            System.out.println("11- Mostrar las factuas que superen el total de $500.000");
            System.out.println("Seleccione una opcion");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:{
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese DNI: ");
                    int dni = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva linea
                    System.out.println("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese Apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.println("Ingrese Domicilio: ");
                    String domicilio = scanner.nextLine();
                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Cliente existente = em.find(Cliente.class, dni);
                        if (existente != null) {
                            System.out.println("El cliente con DNI " + dni + " ya existe.");
                        } else {
                            Cliente nuevoCliente = new Cliente();
                            nuevoCliente.setDni(dni);
                            nuevoCliente.setNombre(nombre);
                            nuevoCliente.setApellido(apellido);
                            nuevoCliente.setDomicilio(domicilio);
                            nuevoCliente.setEstado(true);

                            em.getTransaction().begin();
                            em.persist(nuevoCliente);
                            em.getTransaction().commit();

                            System.out.println("Cliente creado exitosamente.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al crear el cliente: " + e.getMessage());
                    } finally {
                        em.close();
                    }
                    break;
                }
                case 2:{
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese codigo del producto: ");
                    int codigo = scanner.nextInt();
                    scanner .nextLine();

                    System.out.println("Ingrese descripcion del producto: ");
                    String descripcion = scanner.nextLine();

                    System.out.println("Ingrese precio unitario del producto: ");
                    int precioUnitario = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
                    
                    try{
                        List<Producto> resultados = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :codigo", Producto.class)
                            .setParameter("codigo", codigo)
                              .getResultList();

Producto existente = resultados.isEmpty() ? null : resultados.get(0);

                        if (existente != null) {
                            System.out.println("El producto con codigo " + codigo + " ya existe.");
                        } else {
                    Producto nuevoproducto = new Producto();
                    nuevoproducto.setCodigo(codigo);
                    nuevoproducto.setDescripcion(descripcion);
                    nuevoproducto.setPrecioUnitario(precioUnitario);
                    nuevoproducto.setEstado(true);

                    em.getTransaction().begin();
                    em.persist(nuevoproducto);
                    em.getTransaction().commit();

                    System.out.println("Producto creado exitosamente.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al crear el producto: " + e.getMessage());
                    } finally {
                        em.close();
                    }
                    break;
                }

                case 3: {
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese DNI del cliente: ");
                    int dni = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class)
                                .setParameter("dni", dni)
                                .getSingleResult();
                        
                        if (cliente == null) {
                            System.out.println("Cliente no encontrado.");
                            return;
                        }

                        Factura nuevaFactura = new Factura();
                        nuevaFactura.setEstado(true);
                        nuevaFactura.setFecha(LocalDate.now());
                        nuevaFactura.setCliente(cliente);

                        boolean agregarMasProductos = true;
                        while (agregarMasProductos) {
                            System.out.println("Ingrese codigo del producto: ");
                            int codigoProducto = scanner.nextInt();
                            Producto producto = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :codigo AND p.estado = true", Producto.class)
                                    .setParameter("codigo", codigoProducto)
                                    .getSingleResult();

                            if (producto == null) {
                                System.out.println("Producto no encontrado.");
                                continue;
                            }

                            System.out.println("Ingrese cantidad: ");
                            int cantidad = scanner.nextInt();

                            DetalleFactura detalle = new DetalleFactura();
                            detalle.setFactura(nuevaFactura);
                            detalle.setProducto(producto);
                            detalle.setCantidad(cantidad);
                            detalle.setSubtotal(producto.getPrecioUnitario() * cantidad);

                            nuevaFactura.getDetalles().add(detalle);

                            System.out.println("¿Desea agregar otro producto? (s/n): ");
                            agregarMasProductos = scanner.next().equalsIgnoreCase("s");
                        }

                        em.getTransaction().begin();
                        em.persist(nuevaFactura);
                        em.getTransaction().commit();

                        System.out.println("Venta registrada exitosamente.");
                    } catch (Exception e) {
                        System.out.println("Error al registrar la venta: " + e.getMessage());
                    } finally {
                        em.close();
                    }
                    break;
                }

                case 4:{
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese el numero de la factura: ");
                    int numeroFactura = scanner.nextInt();
                    
                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Factura factura = em.find(Factura.class, numeroFactura);

                        if (factura == null) {
                            System.out.println("factura no encontrada.");
                            return;
                        }

                        System.out.println("====== Datos de la factura ======");
                        System.out.println("Numero de factura: " + factura.getId());
                        System.out.println("Fecha: " + factura.getFecha());
                        System.out.println("Cliente: " + factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());

                        System.out.println("==== Detalles ====");
                        for (DetalleFactura detalle : factura.getDetalles()){
                            Producto producto = detalle.getProducto();
                            System.out.println("Producto: " + producto.getDescripcion());
                            System.out.println("Cantidad: " + detalle.getCantidad());
                            System.out.println("Subtotal: " + detalle.getSubtotal());
                            System.out.println("---------------------");
                        }

                    } catch (Exception e) {
                        System.err.println("Error al buscar la factura: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                } break;
                }

                case 5: {
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese el numero de la factura a eliminar: ");
                    int numeroFactura = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Factura factura = em.find(Factura.class, numeroFactura);

                        if (factura == null) {
                            System.out.println("Factura no encontrada.");
                            return;
                        }

                        if (!factura.isEstado()) {
                            System.out.println("La factura ya ha sido eliminada.");
                            return;
                        }
                        em.getTransaction().begin();
                        factura.setEstado(false);
                        em.getTransaction().commit();

                        System.out.println("Factura eliminada exitosamente.");
                    } catch (Exception e) {
                        System.err.println("Error al eliminar la factura: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    }
                    break;
                }
                
                case 6: {
                    scanner = new Scanner(System.in);
                    System.out.println("Ingrese el codigo del producto a eliminar: ");
                    int codigoProducto = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Producto producto = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :codigo", Producto.class)
                                .setParameter("codigo", codigoProducto)
                                .getSingleResult();

                        if (producto == null) {
                            System.out.println("Producto no encontrado.");
                            return;
                        }

                        if (!producto.isEstado()) {
                            System.out.println("El producto ya ha sido eliminado.");
                            return;
                        }

                        em.getTransaction().begin();
                        producto.setEstado(false);
                        em.getTransaction().commit();
                        System.out.println("Producto eliminado exitosamente.");

                    } catch (Exception e) {
                        System.err.println("Error al eliminar el producto: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    } break;
                }

                case 7: {
                    scanner = new Scanner (System.in);
                    System.out.println("Ingrese el DNI del cliente a modificar: ");
                    int dni = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class)
                                .setParameter("dni", dni)
                                .getSingleResult();

                        if (cliente == null) {
                            System.out.println("Cliente no encontrado.");
                            return;
                        }

                        System.out.println("=== Datos actuales del cliente ===");
                        System.out.println("DNI: " + cliente.getDni());
                        System.out.println("Nombre: " + cliente.getNombre());
                        System.out.println("Apellido: " + cliente.getApellido());
                        System.out.println("Domicilio: " + cliente.getDomicilio());



                        System.out.println("Ingrese el nuevo apellido (o presione Enter para mantener): ");
                        scanner.nextLine(); // Consumir la nueva linea
                        String nuevoApellido = scanner.nextLine();
                        if (!nuevoApellido.isBlank()){
                            cliente.setApellido(nuevoApellido);
                        }

                        System.out.println("Ingrese el nuevo domicilio (o presione Enter para mantener): ");
                        String nuevoDomicilio = scanner.nextLine();
                        if (!nuevoDomicilio.isBlank()){
                            cliente.setDomicilio(nuevoDomicilio);
                        }

                        System.out.println("Ingrese el nuevo nombre (o presione Enter para mantener): ");
                        String nuevoNombre = scanner.nextLine();
                        if (!nuevoNombre.isBlank()){
                            cliente.setNombre(nuevoNombre);
                        }


                        em.getTransaction().begin();
                        em.merge(cliente); //Actualiza el cliente en la base
                        em.getTransaction().commit();

                        System.out.println("Datos del cliente actualizados exitosamente.");
                } catch (Exception e) {
                        System.err.println("Error al modificar los datos del cliente: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    } break;
                }   
            
                case 8: {
                    scanner = new Scanner (System.in);
                    System.out.println("Ingrese codigo del producto a modificar: ");
                    int codigoProducto = scanner.nextInt();

                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        Producto producto = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :codigo", Producto.class)
                                .setParameter("codigo", codigoProducto)
                                .getSingleResult();

                        if (producto == null) {
                            System.out.println("Producto no encontrado.");
                            return;
                        }

                        if (!producto.isEstado()) {
                            System.out.println("El producto ha sido eliminado.");
                            return;
                        }

                        System.out.println("=== Datos Actuales ===");
                        System.out.println("Descripcion: " + producto.getDescripcion());
                        System.out.println("Precio Unitario: " + producto.getPrecioUnitario());

                        System.out.println("Ingrese el nuevo precio unitario: ");
                        int nuevoPrecio = scanner.nextInt();

                        if (nuevoPrecio <= 0){
                            System.out.println("El precio debe ser mayor a 0.");
                            return;
                        }

                        em.getTransaction().begin();
                        producto.setPrecioUnitario(nuevoPrecio);
                        em.merge(producto); //Actualiza el producto en la base
                        em.getTransaction().commit();

                        System.out.println("Precio del producto actualizado exitosamente.");
                    } catch (Exception e) {
                        System.err.println("Error al modificar el precio del producto: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    } break;
                }

                case 9:{
                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {

                        List<Factura> facturas = em.createQuery("SELECT f FROM Factura f WHERE f.estado = true", Factura.class).getResultList();

                        if (facturas.isEmpty()) {
                            System.out.println("No hay facturas registradas.");
                            return;
                        }

                        System.out.println("=== Lista de Facturas ===");
                        for (Factura factura : facturas) {
                            System.out.println("Numero de factura: " + factura.getId());
                            System.out.println("Fecha: " + factura.getFecha());
                            System.out.println("Cliente: " + factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
                            System.out.println("Total: " + factura.getTotal());
                            System.out.println("Estado: " + (factura.isEstado() ? "Activo" : "Eliminado"));
                            System.out.println("---------------------");

                            double totalFactura = 0;
                            System.out.println("==== Detalles ====");
                            for (DetalleFactura detalle : factura.getDetalles()){
                                Producto producto = detalle.getProducto();
                                System.out.println("Producto: " + producto.getDescripcion());
                                System.out.println("Cantidad: " + detalle.getCantidad());
                                System.out.println("Subtotal: " + detalle.getSubtotal());
                                System.out.println("---------------------");
                                totalFactura += detalle.getSubtotal();
                            }
                            System.out.println("Total Factura: " + totalFactura);
                            System.out.println("===================================");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al listar las facturas: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    }
                }
                case 10:{
                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c WHERE c.estado = true", Cliente.class).getResultList();

                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                            return;
                        }

                        System.out.println("=== Lista de Clientes ===");
                        for (Cliente cliente : clientes) {
                            System.out.println("DNI: " + cliente.getDni());
                            System.out.println("Nombre: " + cliente.getNombre());
                            System.out.println("Apellido: " + cliente.getApellido());
                            System.out.println("Domicilio: " + cliente.getDomicilio());
                            System.out.println("---------------------");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al listar los clientes: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    }
                    break;
                    }
                case 11:{
                    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

                    try {
                        List<Factura> facturas = em.createQuery("SELECT f FROM Factura f WHERE f.total > 500000 AND f.estado = true", Factura.class).getResultList();

                        if (facturas.isEmpty()) {
                            System.out.println("No hay facturas que superen el total de $500.000.");
                            return;
                        }

                        System.out.println("=== Facturas que superan los $500.000 ===");
                        for (Factura factura : facturas) {
                            System.out.println("Numero de factura: " + factura.getId());
                            System.out.println("Fecha: " + factura.getFecha());
                            System.out.println("Cliente: " + factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
                            System.out.println("Total: " + factura.getTotal());
                            System.out.println("---------------------");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al listar las facturas: ");
                        e.printStackTrace();
                    } finally {
                        em.close();
                    }
                    break;
                }

            }
        } while (opcion != 0);
        scanner.close();
    }
}