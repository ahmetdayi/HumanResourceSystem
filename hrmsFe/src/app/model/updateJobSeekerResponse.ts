import {GetPersonalSkillResponse} from "./getPersonalSkillResponse";

export interface UpdateJobSeekerResponse {

  id: string,

  firstname: string,

  lastName: string,

  email: string,

  birthDay: Date, //backendden localdate gelıyor hangı turle tutabılecegıne bak

  description: string,

  inBlackList: boolean,

  personalSkillList: GetPersonalSkillResponse[]

}
