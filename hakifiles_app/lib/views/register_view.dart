import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';

import '../tools/index.dart';

class RegisterView extends StatelessWidget {
  const RegisterView({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => RegisterFormProvider(),
      child: Builder(
        builder: (context) {
          final registerFormProvider = Provider.of<RegisterFormProvider>(
            context,
            listen: false,
          );
          return Container(
            margin: EdgeInsets.only(top: 100),
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: Center(
              child: ConstrainedBox(
                constraints: BoxConstraints(maxWidth: 370),
                child: Form(
                  key: registerFormProvider.formKey,
                  child: Column(
                    children: [
                      TextFormField(
                        autofillHints: [AutofillHints.email],
                        onChanged:
                            (value) => registerFormProvider.email = value,
                        validator: (value) {
                          if (!EmailValidator.validate(value ?? '')) {
                            return 'Email is not valid';
                          }
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Enter email',
                          label: 'Email',
                          icon: Icons.email_outlined,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        autofillHints: [
                          AutofillHints.username,
                          AutofillHints.name,
                        ],
                        onChanged: (value) => registerFormProvider.name = value,
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Enter your username';
                          }
                          if (value.contains(' ')) {
                            return 'Username contains spaces';
                          }
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Enter username',
                          label: 'Username',
                          icon: Icons.accessibility_new_outlined,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        autofillHints: [AutofillHints.password],
                        onChanged:
                            (value) => registerFormProvider.password = value,
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Enter your password';
                          }
                          return null;
                        },
                        obscureText: true,
                        decoration: CustomInputs.authInputDecoration(
                          hint: '************',
                          label: 'Password',
                          icon: Icons.lock_outline_rounded,
                        ),
                      ),
                      SizedBox(height: 20),
                      OutlinedButton(
                        onPressed: () {
                          final validForm = registerFormProvider.validateForm();
                          if (!validForm) return;

                          Provider.of<AuthProvider>(
                            context,
                            listen: false,
                          ).register(
                            email: registerFormProvider.email,
                            name: registerFormProvider.name,
                            password: registerFormProvider.password,
                          );
                        },
                        child: Text("Register"),
                      ),
                      SizedBox(height: 20),
                      LinkText(
                        text: 'Login',
                        onPresssed: () {
                          NavigationService.navigateTo(HakiRouter.loginRoute);
                        },
                      ),
                    ],
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
