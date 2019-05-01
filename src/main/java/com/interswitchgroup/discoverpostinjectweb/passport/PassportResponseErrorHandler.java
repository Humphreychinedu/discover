package com.interswitchgroup.discoverpostinjectweb.passport;

//import com.interswitchng.base.model.Error;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class PassportResponseErrorHandler extends DefaultResponseErrorHandler {

//    final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public void handleError(ClientHttpResponse response) throws IOException {
//        try {
//            super.handleError(response);
//        } catch (HttpClientErrorException exc) {
//            if (exc.getStatusCode() == HttpStatus.NOT_FOUND) {
//                throw new ResourceNotFoundException("User does not exist");
//            }
//
//            PassportBadRequestResponse body = mapper.readValue(exc.getResponseBodyAsString(),
//                    PassportBadRequestResponse.class);
//            List<Error> errors = null;
//            if (body.getErrors() != null) {
//                errors = new ArrayList<>();
//                for (PassportFieldError passportFieldError : body.getErrors()) {
//                    Error error = new Error(passportFieldError.getFieldName(),
//                            passportFieldError.getMessage());
//                    errors.add(error);
//                }
//            }
//
//            throw new BadRequestException(body.getDescription(), errors);
//        } catch (HttpServerErrorException | UnknownHttpStatusCodeException exc) {
//            throw new FailedDependencyException(exc.getResponseBodyAsString());
//        }
//    }

}
