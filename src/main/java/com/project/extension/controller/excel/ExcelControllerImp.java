package com.project.extension.controller.excel;

import com.project.extension.dto.excel.ExcelImportResponseDto;
import com.project.extension.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelControllerImp implements ExcelControllerDoc {

    private final ExcelService excelService;

    @Override
    public ResponseEntity<ExcelImportResponseDto> inserirPlanilhaCliente(MultipartFile file) {
        ExcelImportResponseDto response = excelService.importarClientes(file);
        return ResponseEntity.ok(response);
    }
}
