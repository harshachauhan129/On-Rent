package au.edu.jcu.flatOnRent.onrent.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    private Pattern email;
    private Pattern name;
    private Pattern password;
    private Pattern phone;
    private Matcher matcher;

    private static final String NAME_PATTERN="^[A-Za-z]{3,25}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9]{3,25}$";
    private static final String PHONE_NUMBER = "^[0-9]{10,10}$";

    public Validations()
    {
        email = Pattern.compile(EMAIL_PATTERN);
        name = Pattern.compile(NAME_PATTERN);
        password = Pattern.compile(PASSWORD_PATTERN);
       phone = Pattern.compile(PHONE_NUMBER);

    }


    public boolean email_validate(final String hex) {

        matcher = email.matcher(hex);
        return matcher.matches();

    }

    public boolean name_validate(final String hex) {

        matcher = name.matcher(hex);
        return matcher.matches();

    }

    public boolean password_validate(final String hex){

        matcher = password.matcher(hex);
        return  matcher.matches();
    }
    public boolean phone_validate(final String hex){

        matcher = phone.matcher(hex);
        return  matcher.matches();
    }
}
