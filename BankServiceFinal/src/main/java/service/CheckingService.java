package service;

import com.example.Util.Util;
import com.example.domain.Account;
import com.example.domain.CheckingAccount;
import com.example.dto.AccountDTO;
import com.example.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("checkingService")
@RequiredArgsConstructor
public class CheckingService {

    private AccountRepository accountRepository;

    private ModelMapper mapper;


    public void create(AccountDTO accountDTO){

        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountDTO.getAccountNumber());

        CheckingAccount checkingAccount = new CheckingAccount();


       while((Util.generateAccountNum().compareTo(accountOptional.get().getAccountNumber()) == 0)){


       }

    }
}
