import { Component, OnInit } from '@angular/core';
import {User} from '../shared/models';
import {UserService} from '../shared/services';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  currentUser: User;
  users: User[] = [];

  constructor(private userService: UserService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.loadAllUsers();
  }

  deleteUser(id: number) {
    // this.userService.delete(id).pipe(first()).subscribe(() => {
    //   this.loadAllUsers();
    // });
  }

  private loadAllUsers() {
    // this.userService.getAll().pipe(first()).subscribe(users => {
    //   this.users = users;
    // });
  }

}
