package com.project.extension.service;

import com.project.extension.dto.excel.ExcelImportResponseDto;
import com.project.extension.entity.Cliente;
import com.project.extension.entity.Endereco;
import com.project.extension.entity.Status;
import com.project.extension.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ClienteRepository clienteRepository;
    private static final String SHEET_NAME = "Clientes";

    public ExcelImportResponseDto importarClientes(MultipartFile file) {
        List<Cliente> clientes = excelToClientes(file);

        clienteRepository.saveAll(clientes);
        return null;
    }

    public List<Cliente> excelToClientes(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            List<Cliente> clientes = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cliente cliente = new Cliente();
                cliente.setNome(row.getCell(0).getStringCellValue());
                cliente.setTelefone(row.getCell(1).getStringCellValue());
                cliente.setEmail(row.getCell(2).getStringCellValue());
                cliente.setCpf("");
                cliente.setStatus("Ativo");

                Endereco endereco = new Endereco();
                endereco.setRua(row.getCell(3).getStringCellValue());
                endereco.setBairro(row.getCell(4).getStringCellValue());
                endereco.setCep(row.getCell(5).getStringCellValue());
                endereco.setComplemento(row.getCell(6).getStringCellValue());
                endereco.setCidade(row.getCell(7).getStringCellValue());
                endereco.setUf(row.getCell(8).getStringCellValue());

            }

            return clientes;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o Excel", e);
        }
    }
}