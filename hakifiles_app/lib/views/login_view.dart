import 'package:flutter/material.dart';
import 'package:hakifiles_app/Services/index.dart';
import 'package:hakifiles_app/providers/index.dart';
import 'package:hakifiles_app/router/index.dart';
import 'package:hakifiles_app/tools/index.dart';
import 'package:provider/provider.dart';

class LoginView extends StatelessWidget {
  const LoginView({super.key});

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context);
    return ChangeNotifierProvider(
      create: (context) => LoginFormProvider(),
      child: Builder(
        builder: (context) {
          final loginFormProvider = Provider.of<LoginFormProvider>(
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
                  key: loginFormProvider.formKey,
                  child: Column(
                    children: [
                      TextFormField(
                        onFieldSubmitted:
                            (value) =>
                                onFormSumit(loginFormProvider, authProvider),
                        onChanged:
                            (value) => loginFormProvider.emailUsername = value,
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Enter your email or password';
                          }
                          return null;
                        },
                        decoration: CustomInputs.authInputDecoration(
                          hint: 'Enter email or username',
                          label: 'Email/Username',
                          icon: Icons.accessibility_new_outlined,
                        ),
                      ),
                      SizedBox(height: 20),
                      TextFormField(
                        onFieldSubmitted:
                            (value) =>
                                onFormSumit(loginFormProvider, authProvider),
                        onChanged:
                            (value) => loginFormProvider.password = value,
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
                        onPressed:
                            () => onFormSumit(loginFormProvider, authProvider),
                        child: Text("Login"),
                      ),
                      SizedBox(height: 20),
                      LinkText(
                        text: 'Register',
                        onPresssed: () {
                          NavigationService.navigateTo(
                            HakiRouter.registerRoute,
                          );
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

  void onFormSumit(
    LoginFormProvider loginFormProvider,
    AuthProvider authProvider,
  ) {
    if (loginFormProvider.validateForm()) {
      authProvider.login(
        loginFormProvider.emailUsername,
        loginFormProvider.password,
      );
    }
  }
}
