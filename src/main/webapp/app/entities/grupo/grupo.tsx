import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrupo } from 'app/shared/model/grupo.model';
import { getEntities } from './grupo.reducer';

export const Grupo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const grupoList = useAppSelector(state => state.grupo.entities);
  const loading = useAppSelector(state => state.grupo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="grupo-heading" data-cy="GrupoHeading">
        <Translate contentKey="labArquiJHipsterApp.grupo.home.title">Grupos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.grupo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/grupo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.grupo.home.createLabel">Create new Grupo</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {grupoList && grupoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.grupoId">Grupo Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.codigo">Codigo</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.horario">Horario</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.curso">Curso</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupo.semestreAcademico">Semestre Academico</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {grupoList.map((grupo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/grupo/${grupo.id}`} color="link" size="sm">
                      {grupo.id}
                    </Button>
                  </td>
                  <td>{grupo.grupoId}</td>
                  <td>{grupo.codigo}</td>
                  <td>{grupo.horario}</td>
                  <td>{grupo.curso ? <Link to={`/curso/${grupo.curso.id}`}>{grupo.curso.id}</Link> : ''}</td>
                  <td>
                    {grupo.semestreAcademico ? (
                      <Link to={`/semestre-academico/${grupo.semestreAcademico.id}`}>{grupo.semestreAcademico.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/grupo/${grupo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/grupo/${grupo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/grupo/${grupo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.grupo.home.notFound">No Grupos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Grupo;
