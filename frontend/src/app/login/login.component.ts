import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService} from '../shared/services';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  wrongCredentials = false;

  usernamePlaceholder = 'إسم المستخدم';
  passwordPlaceholder = 'كلمة المرور';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // reset login status
    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.usernamePlaceholder = 'إسم المستخدم';
    this.passwordPlaceholder = 'كلمة المرور';
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      if (this.f.username.errors) { this.usernamePlaceholder = 'من فضلك أدخل إسم المستخدم'; }
      if (this.f.password.errors) { this.passwordPlaceholder = 'من فضلك أدخل كلمة المرور';  }
      this.loginForm.reset();
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          console.log('subscribe');
          console.log(data);
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.wrongCredentials = true;
          this.loginForm.controls['password'].reset();
          console.log(error);
          this.alertService.error('خطأ في اسم المستخدم أو كلمة السر');
          this.loading = false;
        });
  }
}
