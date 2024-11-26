package com.example.data.protocols.api;

import com.example.domain.dto.BillsDTO;

public interface IConsultCelcoinBillRepository {

    String consultBills(BillsDTO billsDTO);

}
