package com.Parfetch.ParFetch.controller;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;
import com.Parfetch.ParFetch.service.ParcelService;
import com.Parfetch.ParFetch.service.ReceiverService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parcels")
public class ParcelController {

    private final ParcelService parcelService;
    private final ReceiverService receiverService;

    public ParcelController(ParcelService parcelService, ReceiverService receiverService) {
        this.parcelService = parcelService;
        this.receiverService = receiverService;
    }

    @GetMapping("/list")
    public String showAllParcels(@RequestParam(required = false) String keyword, Model model, Authentication auth) {
        List<Parcel> parcels;
        String homeLink;
        String username = auth.getName();

        boolean isReceiver = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECEIVER"));

        if (isReceiver) {
            Receiver receiver = receiverService.findByPhone(username);

            if (receiver == null) {
                model.addAttribute("parcels", List.of());
                model.addAttribute("username", "Unknown Student");
                model.addAttribute("keyword", keyword);
                model.addAttribute("homeLink", "/student-home");
                return "list-parcel-receiver";
            }

            parcels = parcelService.getParcelsByReceiver(receiver);

            if (keyword != null && !keyword.isEmpty()) {
                parcels = parcels.stream()
                        .filter(p ->
                                (p.getTrackingNumber() != null && p.getTrackingNumber().toString().contains(keyword)) ||
                                        (p.getReceiveDate() != null && p.getReceiveDate().contains(keyword)) ||
                                        (p.getReceiverName() != null && p.getReceiverName().toLowerCase().contains(keyword.toLowerCase())) ||
                                        (p.getReceiverPhone() != null && p.getReceiverPhone().toLowerCase().contains(keyword.toLowerCase())) ||
                                        (p.getSenderPhone() != null && p.getSenderPhone().toLowerCase().contains(keyword.toLowerCase()))
                        )
                        .toList();
            }

            model.addAttribute("parcels", parcels);
            model.addAttribute("username", receiver.getName());
            model.addAttribute("keyword", keyword);
            model.addAttribute("homeLink", "/student-home");

            return "list-parcel-receiver";
        }

        // Staff view
        if (keyword != null && !keyword.isEmpty()) {
            parcels = parcelService.searchParcels(keyword);
            homeLink = "/parcels/list";
        } else {
            parcels = parcelService.getAllParcels();
            homeLink = "/index";
        }

        model.addAttribute("parcels", parcels);
        model.addAttribute("username", username);
        model.addAttribute("keyword", keyword);
        model.addAttribute("homeLink", homeLink);

        return "list-parcel";
    }

    @GetMapping("/signup")
    public String showAddParcelForm(Model model, Authentication auth) {
        model.addAttribute("parcel", new Parcel());

        String role = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECEIVER")) ? "RECEIVER" : "STAFF";

        model.addAttribute("role", role);
        return "add-parcel";
    }

    @PostMapping("/save")
    public String saveParcel(@ModelAttribute Parcel parcel) {
        Receiver receiver = receiverService.findByPhone(parcel.getReceiverPhone());

        if (receiver == null) {
            receiver = new Receiver();
            receiver.setPhone(parcel.getReceiverPhone());
            receiver.setName(parcel.getReceiverName());
            receiverService.save(receiver);
        }

        parcel.setReceiver(receiver);
        parcelService.save(parcel);

        return "redirect:/parcels/list";
    }

    @GetMapping("/update")
    public String showUpdateSelectionForm(Model model) {
        model.addAttribute("parcels", parcelService.getAllParcels());
        return "choose-parcel-to-update";
    }

    @GetMapping("/edit/{trackingNumber}")
    public String showUpdateForm(@PathVariable("trackingNumber") int trackingNumber, Model model) {
        Parcel parcel = parcelService.findById(trackingNumber).orElse(null);
        if (parcel == null) {
            throw new IllegalArgumentException("Invalid parcel trackingNumber: " + trackingNumber);
        }
        model.addAttribute("parcel", parcel);
        return "update-parcel";
    }

    @PostMapping("/edit/{trackingNumber}")
    public String updateParcel(@PathVariable("trackingNumber") int trackingNumber,
                               @ModelAttribute("parcel") Parcel parcel,
                               Model model) {

        Parcel existing = parcelService.findById(trackingNumber).orElse(null);
        if (existing != null) {
            existing.setPaymentStatus(parcel.getPaymentStatus());
            existing.setDeliveryStatus(parcel.getDeliveryStatus());
            existing.setSenderPhone(parcel.getSenderPhone());
            existing.setWeight(parcel.getWeight());
            existing.setReceiveDate(parcel.getReceiveDate());

            Receiver receiver = existing.getReceiver();
            if (receiver != null) {
                receiver.setPhone(parcel.getReceiverPhone());
                receiverService.save(receiver);
            }

            existing.setReceiverPhone(parcel.getReceiverPhone());
            parcelService.save(existing);
        }

        return "redirect:/parcels/update";
    }

    @GetMapping("/delete")
    public String showDeleteSelectionForm(Model model) {
        model.addAttribute("parcels", parcelService.getAllParcels());
        return "choose-parcel-to-delete";
    }

    @GetMapping("/delete/{trackingNumber}")
    public String deleteParcel(@PathVariable("trackingNumber") int trackingNumber) {
        Parcel parcel = parcelService.findById(trackingNumber).orElse(null);
        if (parcel != null) {
            parcelService.deleteParcel(parcel);
        }
        return "redirect:/parcels/list";
    }

    @GetMapping("/search")
    public String searchParcels(@RequestParam(required = false) String keyword, Model model) {
        List<Parcel> parcels;
        if (keyword != null && !keyword.trim().isEmpty()) {
            parcels = parcelService.searchParcels(keyword);
        } else {
            parcels = parcelService.getAllParcels();
        }

        model.addAttribute("parcels", parcels);
        model.addAttribute("keyword", keyword);
        model.addAttribute("homeLink", "/parcels/update");
        return "choose-parcel-to-update";
    }
}
