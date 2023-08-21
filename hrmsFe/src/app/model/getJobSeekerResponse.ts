import {GetPersonalSkillResponse} from "./getPersonalSkillResponse";

export interface GetJobSeekerResponse{

  id: string,

  firstname: string,

  lastName: string,

  email: string,

  birthDay: Date, //backendden localdate gelıyor hangı turle tutabılecegıne bak

  description: string,

  inBlackList: boolean,

  personalSkillList: GetPersonalSkillResponse[]

}
