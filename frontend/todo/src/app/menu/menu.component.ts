import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import {MediaMatcher} from '@angular/cdk/layout';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnDestroy {
  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  constructor(public basicAuthenticationService: BasicAuthenticationService,
              changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
                this.mobileQuery = media.matchMedia('(max-width: 600px)');
                this._mobileQueryListener = () => changeDetectorRef.detectChanges();
                this.mobileQuery.addListener(this._mobileQueryListener);
               }

  ngOnDestroy(): void {
  this.mobileQuery.removeListener(this._mobileQueryListener);
}

  ngOnInit(): void {}

}

