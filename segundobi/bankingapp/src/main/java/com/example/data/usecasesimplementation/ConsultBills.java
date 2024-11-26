package com.example.data.usecasesimplementation;

import com.example.data.protocols.api.IConsultCelcoinBillRepository;
import com.example.domain.dto.BillsDTO;
import com.example.domain.usecases.IConsultBills;

public class ConsultBills implements IConsultBills {

    private final IConsultCelcoinBillRepository repository;

    public ConsultBills(IConsultCelcoinBillRepository repository) {
        this.repository = repository;
    }

    @Override
    public String consultBills(BillsDTO billsDTO) {
        return this.repository.consultBills(billsDTO);
    }
}
