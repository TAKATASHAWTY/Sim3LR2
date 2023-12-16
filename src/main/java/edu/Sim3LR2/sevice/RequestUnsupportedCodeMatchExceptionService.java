package edu.Sim3LR2.sevice;

import edu.Sim3LR2.model.Request;
import edu.Sim3LR2.exception.UnsupportedCodeException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class RequestUnsupportedCodeMatchExceptionService implements UnsupportedCodeMatchExceptionService {

    @Override
    public void isUidMatch(Request request) throws UnsupportedCodeException {
        if (Objects.equals(request.getUid(), "123")) {
            throw new UnsupportedCodeException();
        }
    }
}
