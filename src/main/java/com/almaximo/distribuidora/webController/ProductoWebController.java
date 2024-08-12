package com.almaximo.distribuidora.webController;

import com.almaximo.distribuidora.exception.ResourceNotFoundException;
import com.almaximo.distribuidora.model.Producto;
import com.almaximo.distribuidora.model.Proveedor;
import com.almaximo.distribuidora.model.ProveedorProducto;
import com.almaximo.distribuidora.model.TipoProducto;
import com.almaximo.distribuidora.service.ProductoService;
import com.almaximo.distribuidora.service.ProveedorProductoService;
import com.almaximo.distribuidora.service.ProveedorService;
import com.almaximo.distribuidora.service.TipoProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private TipoProductoService tipoProductoService;
    @Autowired
    private ProveedorProductoService proveedorProductoService;
    @Autowired
    private ProveedorService proveedorService;


    @GetMapping
    public String getAllProductos(Model model) {
        List<Producto> productos = productoService.findAllActive();
        List<TipoProducto> tipoProducto = tipoProductoService.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("tipoProducto", tipoProducto);
        model.addAttribute("page", "productos/list");

        return "layouts/main";
    }

    @GetMapping("/{id}")
    public String getProductoById(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findById(id);


        if (producto.isPresent()) {
            List<Proveedor> proveedor = proveedorService.findAll();
            List<ProveedorProducto> proveedoresProducto = proveedorProductoService.findByProductoId(id);
            ProveedorProducto proveedorProducto = new ProveedorProducto();
            proveedorProducto.setProducto(producto.get());
            List<TipoProducto> tipoProducto = tipoProductoService.findAll();

            model.addAttribute("proveedor", proveedor);
            model.addAttribute("tipoProducto", tipoProducto);
            model.addAttribute("proveedorProducto", proveedorProducto);
            model.addAttribute("proveedoresProducto", proveedoresProducto);
            model.addAttribute("producto", producto.get());
            model.addAttribute("page", "productos/form");

            return "layouts/main";
        } else {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
    }


    @GetMapping("/create")
    public String createProductoForm(Model model) {
        List<Proveedor> proveedor = proveedorService.findAll();
        List<TipoProducto> tipoProducto = tipoProductoService.findAll();

        model.addAttribute("tipoProducto", tipoProducto);
        model.addAttribute("proveedor", proveedor);

        // Verificar si se está editando un producto existente
        if (model.containsAttribute("producto")) {
            Producto producto = (Producto) model.getAttribute("producto");

            // Carga los ProveedorProducto solo si el producto ya existe
            if (producto != null && producto.getId() != null) {
                List<ProveedorProducto> proveedorProducto = proveedorProductoService.findByProductoId(producto.getId());
                model.addAttribute("proveedorProducto", proveedorProducto);
            } else {
                model.addAttribute("proveedorProducto", new ArrayList<>());
            }
        } else {
            // Si no hay un producto en el modelo, se añade uno nuevo para la creación
            model.addAttribute("producto", new Producto());
        }

        model.addAttribute("page", "productos/form");

        // Devolver la vista principal con el fragmento del formulario de productos
        return "layouts/main";
    }


    @PostMapping("/save")
    public String saveProducto(@ModelAttribute("producto") @Valid Producto producto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            model.addAttribute("page", "productos/form");
            redirectAttributes.addFlashAttribute("flashError", "Hay algunos campos errorenos.");
            return "redirect:/productos/create";
        }

        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute("flashMessage", "Producto guardado!");

        } catch (Exception e) {
            model.addAttribute("producto", producto);
            model.addAttribute("page", "productos/form");
            redirectAttributes.addFlashAttribute("flashError", "Error al guardar el producto.");

            return "redirect:/productos/create";
        }

        return "redirect:/productos/" + producto.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Optional<Producto> productoOpt = productoService.findById(id);
        if (productoOpt.isPresent()) {
            productoService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashMessage", "Producto eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("flashError", "Producto no encontrado.");
        }

        return "redirect:/productos";
    }


    @GetMapping("/list")
    public String listProductos(@RequestParam(required = false) String clave, @RequestParam(required = false) Long tipoProducto, Model model) {
        List<Producto> productos = productoService.findByClaveAndTipoProducto(clave, tipoProducto);
        List<TipoProducto> tiposProducto = tipoProductoService.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("tipoProducto", tiposProducto);
        model.addAttribute("page", "productos/list");

        return "layouts/main";
    }


}
