package edu.Sim3LR2.sevice;

import edu.Sim3LR2.exception.UnsupportedCodeException;
import edu.Sim3LR2.model.Request;
import org.springframework.stereotype.Service;

@Service
public interface UnsupportedCodeMatchExceptionService {

    void isUidMatch(Request request) throws UnsupportedCodeException;
}
