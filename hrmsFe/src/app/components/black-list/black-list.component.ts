import {Component, OnDestroy, OnInit} from '@angular/core';
import {GetBlackListResponse} from "../../model/getBlackListResponse";
import {BlackListService} from "../../services/black-list.service";
import {ToastrService} from "ngx-toastr";
import {BehaviorSubject, Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-black-list',
  templateUrl: './black-list.component.html',
  styleUrls: ['./black-list.component.css']
})
export class BlackListComponent implements OnInit, OnDestroy {
  private allBlackListSubject: BehaviorSubject<GetBlackListResponse[]> = new BehaviorSubject([]);
  allBlackList$ = this.allBlackListSubject.asObservable();

  private subscriptions: Subscription[] = [];

  constructor(
    private blacklistService: BlackListService,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
   
    this.fetchAllBlackList();

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  fetchAllBlackList(): void {
    const subscription = this.blacklistService.findAllBlackList().subscribe({
      next: value => {
        console.log(value);
        this.allBlackListSubject.next(value);
      },
      error: err => {
        this.toastr.error(`${err.error}`, 'An Error Occurred. Please Try Again.');
      }
    });

    this.subscriptions.push(subscription);
  }
}
