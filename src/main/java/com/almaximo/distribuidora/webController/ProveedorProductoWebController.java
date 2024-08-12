package com.almaximo.distribuidora.webController;

import com.almaximo.distribuidora.model.Producto;
import com.almaximo.distribuidora.model.Proveedor;
import com.almaximo.distribuidora.model.ProveedorProducto;
import com.almaximo.distribuidora.service.ProveedorProductoService;
import com.almaximo.distribuidora.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/proveedorProducto")
public class ProveedorProductoWebController {


    @Autowired
    ProveedorProductoService proveedorProductoService;
    @Autowired
    ProveedorService proveedorService;

    @PostMapping("/saveProveedor")
    public String saveProveedor(@ModelAttribute("proveedorProducto") @Valid ProveedorProducto proveedorProducto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flashError", "Error al guardar el proveedor.");
            return "redirect:/productos/" + proveedorProducto.getProducto().getId();
        }
        // Log para verificar si el proveedor fue correctamente asignado
        if (proveedorProducto.getProveedor() == null || proveedorProducto.getProveedor().getId() == null) {
            redirectAttributes.addFlashAttribute("flashError", "Proveedor no asignado correctamente.");
            return "redirect:/productos/" + proveedorProducto.getProducto().getId();
        }
        // Buscar el proveedor en la base de datos y asignarlo al objeto proveedorProducto
        Optional<Proveedor> proveedorOpt = proveedorService.findById(proveedorProducto.getProveedor().getId());
        if (proveedorOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("flashError", "Proveedor no encontrado.");
            return "redirect:/productos/" + proveedorProducto.getProducto().getId();
        }
        proveedorProducto.setProveedor(proveedorOpt.get());


        try {
            proveedorProductoService.save(proveedorProducto);

        } catch (Exception e) {
            // ERROR MESSAGE
            redirectAttributes.addFlashAttribute("flashError", "Error al guardar el Proveedor del producto.");
            return "redirect:/productos/" + proveedorProducto.getProducto().getId();
        }

        redirectAttributes.addFlashAttribute("flashMessage", "Proveedor guardado!");

        return "redirect:/productos/" + proveedorProducto.getProducto().getId();
    }


    @GetMapping("/delete/{id}")
    public String deleteProveedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<ProveedorProducto> productoOpt = proveedorProductoService.findById(id);
        if (productoOpt.isPresent()) {
            try {
                Long productIdRef = productoOpt.get().getProducto().getId();

                proveedorProductoService.deleteById(id);
                redirectAttributes.addFlashAttribute("flashMessage", "Proveedor eliminado correctamente.");

                return "redirect:/productos/" + productIdRef;
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flashError", "No se pudo eliminar el proveedor.");
                return "redirect:/productos/" + productoOpt.get().getProducto().getId();
            }
        } else {
            redirectAttributes.addFlashAttribute("flashError", "Proveedor no encontrado.");
            return "redirect:/productos";
        }
    }


}
