import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  name: string = 'مصطفى فطين';
  profileForm: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.profileForm = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  changePassword() {
    
  }

}
