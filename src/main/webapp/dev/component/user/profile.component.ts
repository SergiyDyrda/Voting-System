import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {ProfileService} from "../../service/profile.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ExceptionService} from "../../service/exception.service";


@Component({
    templateUrl: "../../../templates/user/profile.html"
})
export class ProfileComponent implements OnInit{

    private profileForm: FormGroup = this.formBuilder.group({
        'name': ['', Validators.required],
        'email': ['', Validators.required],
        'password': ['', Validators.required]
    });

    constructor(private formBuilder: FormBuilder,
                private profileService: ProfileService,
                private router: Router,
                private authService: AuthService,
                private exceptionService: ExceptionService) {
    }

    ngOnInit(): void {
        this.profileService.getOwnProfile().subscribe(
            res => {
                let auth = res.json();
                this.authService.authenticatedAs = auth;
                this.profileForm.patchValue({
                    id: auth.id,
                    name: auth.name,
                    email: auth.email,
                });
            }
        )
    }

    save() {
        this.profileService.saveOwnProfile(this.profileForm.value).subscribe(
            res => {
                this.ngOnInit();
                this.router.navigate(['/restaurant-list']);

            },
            err => {
                this.exceptionService.onError(err);
            }
        );
    }

}