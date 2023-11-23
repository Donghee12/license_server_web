package com.example.demo.controller;


import com.example.demo.dataclass.ClientInfo;
import com.example.demo.dataclass.ClientInfoDTO;
import com.example.demo.repository.ClientInfoRepository;
import com.example.demo.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ClientController {

    @Autowired
    private ClientInfoService clientInfoService;

    private final ClientInfoRepository clientInfoRepository;

    @Autowired
    public ClientController(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }
    @GetMapping("/clients_info")
    public String getAdminClientsInfoPage(Model model) {
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        model.addAttribute("clientInfoList", clientInfoList);
        return "admin/clients_info";
    }

    @GetMapping("/{email}")
    public ResponseEntity<ClientInfoDTO> getClientInfo(@PathVariable String email) {
        Optional<ClientInfoDTO> clientInfoDTOOptional = clientInfoService.getClientInfoByEmail(email);

        return clientInfoDTOOptional.map(clientInfoDTO ->
                        new ResponseEntity<>(clientInfoDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}