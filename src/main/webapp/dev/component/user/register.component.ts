import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {ExceptionService} from "../../service/exception.service";
import {AuthService} from "../../service/auth.service";


@Component({
    templateUrl: "../../../templates/user/register.html"
})
export class RegisterComponent implements OnInit {

    private profileForm: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private userService: UserService,
                private router: Router,
                private exceptionService: ExceptionService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.profileForm = this.formBuilder.group({
            'name': ['', Validators.required],
            'email': ['', Validators.required],
            'password': ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(64)])],
        });
    }

    save() {
        this.userService.registerUser(this.profileForm.value).subscribe(
            res => {
                this.router.navigate(['/login']);
            },
            err => {
                this.exceptionService.onError(err);
            }
        );
    }

}